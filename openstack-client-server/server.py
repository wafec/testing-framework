from flask import Flask, jsonify, request, abort
from keystoneclient.v3 import client as keystone_cli
from keystoneauth1.identity import v3
from keystoneauth1 import session
from novaclient import client as nova_cli
from glanceclient import client as glance_cli
from neutronclient.v2_0 import client as neutron_cli
from models import Session, OSTest, OSFlavor, OSImage, OSNetwork, OSServer
import tempfile
import base64
import pickle


app = Flask(__name__)


@app.route('/')
def index():
    return "Hello, World!"


def _create_session(test_id):
    sess = Session()
    test = sess.query(OSTest).filter(OSTest.id == test_id).first()
    auth = v3.Password(auth_url=test.auth_url,
                       username=test.username,
                       password=test.password,
                       user_domain_name=test.user_domain_name,
                       project_name=test.project_name,
                       project_domain_name=test.project_domain_name)
    os_sess = session.Session(auth=auth)
    return os_sess


def _os_flavor_to_json(flavor):
    return {"id": flavor.id, "name": flavor.name, "ram": flavor.ram, "vcpus": flavor.vcpus, "disk": flavor.disk}


@app.route('/flavors', methods=['POST'])
def flavor_create():
    if not request.json:
        abort(400)
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    flavor_dao = OSFlavor(
        name=request.json['name'],
        ram=int(request.json['ram']),
        vcpus=int(request.json['vcpus']),
        disk=int(request.json['disk']),
        test_id=int(test_id)
    )
    flavor = nova.flavors.create(name=flavor_dao.name, ram=flavor_dao.ram, vcpus=flavor_dao.vcpus, disk=flavor_dao.disk)
    if flavor:
        db = Session()
        flavor_dao.uid = flavor.id
        db.add(flavor_dao)
        db.commit()
        return jsonify(_os_flavor_to_json(flavor)), 201
    else:
        abort(401)


@app.route('/flavors')
def flavor_list():
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    flavors = nova.flavors.list()
    result = list()
    for flavor in flavors:
        result.append(_os_flavor_to_json(flavor))
    return jsonify(result), 201


@app.route('/flavors', methods=['DELETE'])
def flavor_delete():
    test_id = request.args.get('test_id')
    flavor_name = request.args.get('flavor_name')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    flavor = nova.flavors.find(name=flavor_name)
    nova.flavors.delete(flavor)
    if flavor:
        db = Session()
        flavor_dao = db.query(OSFlavor).filter(OSFlavor.name == flavor.name).first()
        db.delete(flavor_dao)
        db.commit()
    return jsonify(success=True)


def _os_image_to_json(image):
    return {"id": image.id, "name": image.name, "disk_format": image.disk_format,
            "container_format": image.container_format}


# Glance API Documentation = https://docs.openstack.org/python-glanceclient/latest/reference/apiv2.html
@app.route('/images', methods=['POST'])
def image_create():
    if not request.json:
        abort(400)
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    glance = glance_cli.Client('2', session=sess)
    image_dao = OSImage(
        name=request.json['name'],
        disk_format=request.json['disk_format'],
        container_format=request.json['container_format'],
        test_id=int(test_id)
    )
    image = glance.images.create(name=image_dao.name, disk_format=image_dao.disk_format,
                                 container_format=image_dao.container_format)
    if image:
        data = request.json["data"]
        decoded_data = base64.b64decode(data)
        with tempfile.NamedTemporaryFile() as temp:
            temp.write(decoded_data)
            glance.images.upload(image.id, temp)
        db = Session()
        image_dao.uid = image.id
        db.add(image_dao)
        db.commit()
        return jsonify(_os_image_to_json(image)), 201
    else:
        abort(401)


@app.route('/images')
def image_list():
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    glance = glance_cli.Client('2', session=sess)
    images = glance.images.list()
    result = list()
    for image in images:
        result.append(_os_image_to_json(image))
    return jsonify(result), 201


@app.route('/images', methods=['DELETE'])
def image_delete():
    test_id = request.args.get('test_id')
    image_name = request.args.get('image_name')
    sess = _create_session(int(test_id))
    glance = glance_cli.Client('2', session=sess)
    db = Session()
    image_dao = db.query(OSImage).filter(OSImage.name == image_name).first()
    glance.images.delete(image_dao.uid)
    db.delete(image_dao)
    db.commit()
    return jsonify(success=True)


def _os_server_to_json(server):
    image_id = None
    if server.image and 'id' in server.image:
        image_id = server.image['id']
    return {"name": server.name, "image": image_id, "flavor": server.flavor['id'], "id": server.id}


@app.route('/servers')
def server_list():
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    servers = nova.servers.list()
    result = list()
    for server in servers:
        result.append(_os_server_to_json(server))
    return jsonify(result), 201


@app.route('/servers', methods=['POST'])
def server_create():
    if not request.json:
        abort(400)
    test_id = request.args.get('test_id')
    db = Session()
    flavor_dao = db.query(OSFlavor).filter(OSFlavor.name == request.json['flavor']).first()
    image_dao = db.query(OSImage).filter(OSImage.name == request.json['image']).first()
    network_dao = db.query(OSNetwork).filter(OSNetwork.name == request.json['network']).first()
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    server_dao = OSServer(
        name=request.json['name'],
        flavor=flavor_dao,
        image=image_dao,
        network=network_dao,
        test_id=int(test_id)
    )
    server = nova.servers.create(name=server_dao.name, flavor=flavor_dao.uid, image=image_dao.uid,
                                 network=network_dao.uid)
    if server:
        server_dao.uid = server.id
        db.add(server_dao)
        db.commit()
        return jsonify(_os_server_to_json(server)), 201
    else:
        print('error')
        abort(401)


def _os_network_to_json(network):
    return {"id": network['id'], "name": network['name'], "project": network['project_id']}


@app.route('/networks')
def network_list():
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    neutron = neutron_cli.Client(session=sess)
    networks = neutron.list_networks()
    result = list()
    for network in networks['networks']:
        result.append(_os_network_to_json(network))
    return jsonify(result), 201


if __name__ == "__main__":
    app.run(debug=True)
