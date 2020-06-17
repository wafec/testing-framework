from flask import Flask, jsonify, request, abort
from keystoneclient.v3 import client as keystone_cli
from keystoneauth1.identity import v3
from keystoneauth1 import session
from novaclient import client as nova_cli

from models import Session, OSTest, OSFlavor

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


@app.route('/flavors', methods=['POST'])
def flavor_create():
    if not request.json:
        abort(400)
    test_id = request.json['test_id']
    sess = _create_session(int(test_id))
    nova = nova_cli.Client(session=sess, version='2.1')
    flavor_dao = OSFlavor(
        name=request.json['name'],
        ram=int(request.json['ram']),
        vcpus=int(request.json['vcpus']),
        disk=int(request.json['disk'])
    )
    flavor = nova.flavors.create(name=flavor_dao.name, ram=flavor_dao.ram, vcpus=flavor_dao.vcpus, disk=flavor_dao.disk)
    if flavor:
        db = Session()
        db.add(flavor_dao)
        return jsonify({'flavor_id': flavor.id}), 201
    else:
        abort(401)


if __name__ == "__main__":
    app.run(debug=True)
