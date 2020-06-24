from flask import Flask, jsonify, request, abort
from werkzeug.exceptions import HTTPException
from keystoneclient.v3 import client as keystone_cli
from keystoneauth1.identity import v3
from keystoneauth1 import session
from novaclient import client as nova_cli
from glanceclient import client as glance_cli
from glanceclient.exc import ClientException as glance_cli_exc
from neutronclient.v2_0 import client as neutron_cli
from models import Session, OSTest, OSFlavor, OSImage, OSNetwork, OSServer, OSTestLog
import tempfile
import base64
import pickle
import json
import traceback

app = Flask(__name__)


# https://stackoverflow.com/questions/21638922/custom-error-message-json-object-with-flask-restful
def _make_error(status_code, sub_code, message, action):
    response = jsonify({
        "status": status_code,
        "sub_code": sub_code,
        "message": message,
        "action": action
    })
    response.status_code = status_code
    return response


@app.route('/')
def index():
    return "Hello, World!"


def _create_test_log(sess, test_id, log):
    log_dao = OSTestLog()
    log_dao.test_id = int(test_id)
    log_dao.log = log

    sess.add(log_dao)

    return log_dao


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


def _flavor_dao_create(sess, flavor, test_id, or_update=False):
    flavor_dao = OSFlavor()
    if or_update:
        flavor_result = sess.query(OSFlavor).filter(OSFlavor.name == flavor.name).first()
        if flavor_result:
            flavor_dao = flavor_result

    flavor_dao.name = flavor.name
    flavor_dao.ram = flavor.ram
    flavor_dao.vcpus = flavor.vcpus
    flavor_dao.disk = flavor.disk
    flavor_dao.test_id = int(test_id)
    flavor_dao.uid = flavor.id

    sess.add(flavor_dao)

    return flavor_dao


@app.route('/flavors', methods=['POST'])
def flavor_create():
    if not request.json:
        abort(400)
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')

    flavor = nova.flavors.create(name=request.json['name'], ram=request.json['ram'],
                                 vcpus=request.json['vcpus'], disk=request.json['disk'])
    if flavor:
        db = Session()
        flavor_dao = _flavor_dao_create(flavor, test_id, db)
        _create_test_log(db, test_id, 'Flavor CREATE %s' % repr(flavor_dao))
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
        _create_test_log(db, test_id, 'Flavor DELETE %s' % repr(flavor_dao))
        db.delete(flavor_dao)
        db.commit()
    return jsonify(success=True)


@app.route('/flavors/details')
def flavor_details():
    test_id = request.args.get('test_id')
    flavor_name = request.args.get('flavor_name')
    flavor_id = request.args.get('flavor_id')
    search_opts = {}
    if flavor_name:
        search_opts['name'] = flavor_name
    if flavor_id:
        search_opts['id'] = flavor_id
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    flavor = nova.flavors.find(**search_opts)
    if flavor:
        return jsonify(_os_flavor_to_json(flavor)), 201
    abort(404)


def _os_image_to_json(image):
    return {"id": image.id, "name": image.name, "disk_format": image.disk_format,
            "container_format": image.container_format}


def _image_dao_create(sess, image, test_id, or_update=False):
    image_dao = OSImage()
    if or_update:
        image_result = sess.query(OSImage).filter(OSImage.name == image.name).first()
        if image_result:
            image_dao = image_result
    image_dao.name = image.name
    image_dao.disk_format = image.disk_format
    image_dao.container_format = image.container_format
    image_dao.test_id = int(test_id)
    image_dao.uid = image.id

    sess.add(image_dao)

    return image_dao


# Glance API Documentation = https://docs.openstack.org/python-glanceclient/latest/reference/apiv2.html
@app.route('/images', methods=['POST'])
def image_create():
    if not request.json:
        abort(400)
    test_id = request.args.get('test_id')
    sess = _create_session(int(test_id))
    glance = glance_cli.Client('2', session=sess)

    image = glance.images.create(name=request.json['name'], disk_format=request.json['disk_format'],
                                 container_format=request.json['container_format'])
    if image:
        data = request.json["data"]
        decoded_data = base64.b64decode(data)
        with tempfile.NamedTemporaryFile() as temp:
            temp.write(decoded_data)
            glance.images.upload(image.id, temp)
        db = Session()
        image_dao = _image_dao_create(db, image, test_id)
        _create_test_log(db, test_id, 'Image CREATE %s' % repr(image_dao))
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


@app.route('/images/details')
def image_details():
    test_id = request.args.get('test_id')
    image_name = request.args.get('image_name')
    image_id = request.args.get('image_id')
    search_opts = {}
    if image_name:
        search_opts['name'] = image_name
    if image_id:
        search_opts['id'] = image_id
    sess = _create_session(int(test_id))
    glance = glance_cli.Client('2', session=sess)
    images = glance.images.list(filters=search_opts)
    if images:
        result = list()
        for image in images:
            result.append(_os_image_to_json(image))
        if len(result):
            return jsonify(result[0]), 201
    abort(404)


@app.route('/images', methods=['DELETE'])
def image_delete():
    test_id = request.args.get('test_id')
    image_name = request.args.get('image_name')
    sess = _create_session(int(test_id))
    glance = glance_cli.Client('2', session=sess)
    db = Session()
    image_dao = db.query(OSImage).filter(OSImage.name == image_name).first()
    glance.images.delete(image_dao.uid)
    _create_test_log(db, test_id, 'Image DELETE %s' % repr(image_dao))
    db.delete(image_dao)
    db.commit()
    return jsonify(success=True)


def _os_server_to_json(server):
    image_id = None
    if server.image and 'id' in server.image:
        image_id = server.image['id']
        print(server.networks)
    return {"name": server.name, "image": image_id, "flavor": server.flavor['id'], "id": server.id,
            "status": server.status, "power_state": str(getattr(server, 'OS-EXT-STS:power_state')),
            "network": "",
            "task_state": getattr(server, 'OS-EXT-STS:task_state'), "vm_state": getattr(server, 'OS-EXT-STS:vm_state')}


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


def _server_dao_create(sess, server, test_id, image, flavor, network, or_update=False):
    server_dao = OSServer()
    if or_update:
        server_result = sess.query(OSServer).filter(OSServer.name == server.name).first()
        if server_result:
            server_dao = server_result

    server_dao.uid = server.id
    server_dao.name = server.name
    server_dao.image = image
    server_dao.flavor = flavor
    server_dao.network = network
    server_dao.test_id = int(test_id)

    sess.add(server_dao)

    return server_dao


@app.route('/servers', methods=['POST'])
def server_create():
    if not request.json:
        abort(400)
    test_id = request.args.get('test_id')
    db = Session()
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    glance = glance_cli.Client('2', session=sess)
    neutron = neutron_cli.Client(session=sess)
    flavor = nova.flavors.find(name=request.json['flavor'])
    image = list(glance.images.list(filters={"name": request.json['image']}))[0]
    network = neutron.find_resource('network', request.json['network'])
    server = nova.servers.create(name=request.json['name'], flavor=flavor.id, image=image.id,
                                 network=network['id'])
    if server:
        server_dao = _server_dao_create(db, server, test_id,
                                        image=_image_dao_create(db, image, test_id, True),
                                        flavor=_flavor_dao_create(db, flavor, test_id, True),
                                        network=_network_dao_create(db, network, test_id, True))
        _create_test_log(db, test_id, 'Server CREATE %s' % repr(server_dao))
        db.commit()
        return jsonify(_os_server_to_json(server)), 201
    else:
        abort(401)


@app.route('/servers/details')
def server_details():
    test_id = request.args.get('test_id')
    server_name = request.args.get('server_name')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        return jsonify(_os_server_to_json(servers[0])), 201
    else:
        abort(404)


@app.route('/servers', methods=['DELETE'])
def server_delete():
    test_id = request.args.get('test_id')
    server_name = request.args.get('server_name')
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        nova.servers.delete(server.id)
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.name == server_name).first()
        if server_dao:
            db.delete(server_dao)
            _create_test_log(db, test_id, 'Server DELETE %s' % repr(server_dao))
            db.commit()
        return jsonify(success=True)
    else:
        return abort(404)


def _os_network_to_json(network):
    return {"id": network['id'], "name": network['name'], "project": network['project_id']}


def _network_dao_create(sess, network, test_id, or_update=False):
    network_dao = OSNetwork()
    if or_update:
        network_result = sess.query(OSNetwork).filter(OSNetwork.name == network['name']).first()
        if network_result:
            network_dao = network_result

    network_dao.uid = network['id']
    network_dao.name = network['name']
    network_dao.project_uid = network['project_id']
    network_dao.test_id = int(test_id)

    sess.add(network_dao)

    if not or_update:
        _create_test_log(sess, test_id, 'Network CREATE %s' % repr(network_dao))

    return network_dao


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


@app.route('/networks/details')
def network_details():
    test_id = request.args.get('test_id')
    network_name = request.args.get('network_name')
    network_id = request.args.get('network_id')
    search_opts = {}
    if network_name:
        search_opts['name'] = network_name
    if network_id:
        search_opts['id'] = network_id
    sess = _create_session(int(test_id))
    neutron = neutron_cli.Client(session=sess)
    networks = neutron.list_networks(**search_opts)
    if networks and networks['networks']:
        result = list()
        for network in networks['networks']:
            result.append(_os_network_to_json(network))
        if len(result):
            return jsonify(result[0]), 201
    abort(404)


@app.route('/servers/resize', methods=['POST'])
def server_resize():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    flavor_name = request.json['flavor']
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    flavor = nova.flavors.find(name=flavor_name)
    servers = nova.servers.list(search_opts={"name": server_name})
    if flavor and servers and len(servers):
        server = servers[0]
        if server.flavor['id'] == flavor.id:
            abort(401)
        else:
            server.resize(flavor=flavor.id)
            db = Session()
            server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
            flavor_dao = db.query(OSFlavor).filter(OSFlavor.uid == flavor.id).first()
            _create_test_log(db, test_id, 'Server %s RESIZE %s' % (repr(server_dao), repr(flavor_dao)))
            server_dao.flavor_alt = server_dao.flavor
            server_dao.flavor = flavor_dao
            db.add(server_dao)
            db.commit()
            return jsonify(success=True), 201
    else:
        abort(404)


@app.route('/servers/resize', methods=['PUT'])
def server_confirm_or_reject_resize():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    confirm = request.json['confirm']
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        if confirm:
            server.confirm_resize()
            _create_test_log(db, test_id, 'Server %s CONFIRM RESIZE' % repr(server_dao))
            server_dao.flavor_alt = None
        else:
            server.revert_resize()
            _create_test_log(db, test_id, 'Server %s REVERT RESIZE' % repr(server_dao))
            server_dao.flavor = server.flavor_alt
            server_dao.flavor_alt = None
        db.commit()
        return jsonify(success=True), 201
    else:
        abort(404)


@app.route('/servers/suspend', methods=['POST'])
def server_suspend():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        if server:
            server.suspend()
            db = Session()
            server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
            _create_test_log(db, test_id, 'Server %s SUSPEND' % repr(server_dao))
            db.commit()
            return jsonify(success=True)
        else:
            abort(404)
    else:
        abort(404)


@app.route('/servers/resume', methods=['POST'])
def server_resume():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        server.resume()
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s RESUME' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(404)


@app.route('/servers/pause', methods=['POST'])
def server_pause():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        server.pause()
        db = Session()
        server_dao = db.query(OSServer).query(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s PAUSE' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(404)


@app.route('/servers/unpause', methods=['POST'])
def server_unpause():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        server.unpause()
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s UNPAUSE' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(404)


@app.route('/servers/shelve', methods=['POST'])
def server_shelve():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json["server"]
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        nova.servers.shelve(server)
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s SHELVE' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(404)


@app.route('/servers/unshelve', methods=['POST'])
def server_unshelve():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        nova.servers.unshelve(server.id)
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s UNSHELVE' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(404)


@app.route('/servers/shelve-offload', methods=["POST"])
def server_shelve_offload():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json["server"]
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        server.shelve_offload()
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s SHELVE OFFLOAD' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(404)


@app.route('/servers/stop', methods=["POST"])
def server_stop():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        server.stop()
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s STOP' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(401)


@app.route('/servers/start', methods=["POST"])
def server_start():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    if servers and len(servers):
        server = servers[0]
        server.start()
        db = Session()
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        _create_test_log(db, test_id, 'Server %s START' % repr(server_dao))
        db.commit()
        return jsonify(success=True)
    else:
        abort(401)


@app.route('/servers/rebuild', methods=['POST'])
def server_rebuild():
    test_id = request.args.get('test_id')
    if not request.json:
        abort(401)
    server_name = request.json['server']
    image_name = request.json['image']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client('2.1', session=sess)
    servers = nova.servers.list(search_opts={"name": server_name})
    glance = glance_cli.Client('2', session=sess)
    images = list(glance.images.list(filters={"name": image_name}))
    if servers and len(servers) and images and len(images):
        server = servers[0]
        image = images[0]
        db = Session()
        image_dao = _image_dao_create(db, image, test_id, True)
        server_dao = db.query(OSServer).filter(OSServer.uid == server.id).first()
        if not server_dao:
            abort(404)
        server_dao.image = image_dao
        server.rebuild(image)
        db.add(server_dao)
        db.commit()
        return jsonify(success=True)
    else:
        abort(401)


@app.errorhandler(nova_cli.exceptions.ClientException)
def error_handler_nova(e):
    return _make_error(401, 11, str(e), traceback.format_exc())


@app.errorhandler(glance_cli_exc)
def error_handler_glance(e):
    return _make_error(401, 12, str(e), traceback.format_exc())


@app.errorhandler(neutron_cli.exceptions.NeutronClientException)
def error_handler_neutron(e):
    return _make_error(401, 13, str(e), traceback.format_exc())


@app.errorhandler(Exception)
def error_handler_any(e):
    return _make_error(401, 10, str(e), traceback.format_exc())


if __name__ == "__main__":
    app.run(debug=True)
