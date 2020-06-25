from flask import Blueprint, jsonify
from cinderclient import client as cinder_cli
from commons import _create_session


storage_api = Blueprint('storage_api', __name__)
CINDER_VERSION = '3'


def _volume_to_json(volume):
    print(dir(volume))
    return {
        "id": volume.id,
        "name": volume.name
    }


@storage_api.route('/storage/<test_id>')
def storage_list(test_id):
    sess = _create_session(test_id)
    cinder = cinder_cli.Client(CINDER_VERSION, session=sess)
    volumes = cinder.volumes.list()
    return jsonify(map(_volume_to_json, volumes))
