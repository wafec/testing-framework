from flask import Blueprint, jsonify, request, abort
from cinderclient import client as cinder_cli
from commons import _create_session, _create_test_log
from models import Session, OSVolume


storage_api = Blueprint('storage_api', __name__)
CINDER_VERSION = '3'


def _volume_to_json(volume):
    return {
        "id": volume.id,
        "name": volume.name,
        "status": volume.status,
        "size": volume.size,
        "availability_zone": volume.availability_zone
    }


@storage_api.route('/volumes/<test_id>')
def volume_list(test_id):
    sess = _create_session(test_id)
    cinder = cinder_cli.Client(CINDER_VERSION, session=sess)
    volumes = cinder.volumes.list()
    return jsonify(list(map(_volume_to_json, volumes)))


def _volume_dao_create(transaction, test_id, volume, or_update=False):
    volume_dao = OSVolume()
    if or_update:
        volume_result = transaction.query(OSVolume).filter(OSVolume.name == volume.name).first()
        if volume_result:
            volume_dao = volume_result

    volume_dao.test_id = test_id
    volume_dao.uid = volume.id
    volume_dao.name = volume.name
    volume_dao.size = volume.size
    volume_dao.availability_zone = volume.availability_zone
    volume_dao.status = volume.status

    transaction.add(volume_dao)

    if not or_update:
        _create_test_log(transaction, test_id, 'Volume %s CREATE' % repr(volume_dao))

    return volume_dao


@storage_api.route('/volumes/<test_id>', methods=['POST'])
def volume_create(test_id):
    if not request.json:
        abort(401)
    name = request.json['name']
    size = request.json['size']
    availability_zone = request.json['zone']
    sess = _create_session(test_id)
    cinder = cinder_cli.Client(CINDER_VERSION, session=sess)
    result = cinder.volumes.create(name=name, size=size, availability_zone=availability_zone)
    if result:
        volumes = cinder.volumes.list(search_opts={"id": result.id})
        if volumes and len(volumes):
            volume = volumes[0]
            transaction = Session()
            _volume_dao_create(transaction, test_id, volume)
            transaction.commit()
            return jsonify(_volume_to_json(volume))
        else:
            abort(501)
    else:
        abort(501)


@storage_api.route('/volumes/<test_id>/volume/', methods=['delete'])
def volume_delete(test_id):
    if not request.json:
        abort(401)
    name = request.json['name']
    sess = _create_session(test_id)
    cinder = cinder_cli.Client(CINDER_VERSION, session=sess)
    volumes = cinder.volumes.list(search_opts={"name": name})
    if volumes and len(volumes):
        volume = volumes[0]
        id = volume.id
        cinder.volumes.delete(volume)
        transaction = Session()
        volume_dao = transaction.query(OSVolume).filter(OSVolume.uid == id).first()
        _create_test_log(transaction, test_id, 'Volume %s DELETE' % repr(volume_dao))
        transaction.delete(volume_dao)
        transaction.commit()
        return jsonify(success=True)
    else:
        abort(404)


@storage_api.route('/volumes/<test_id>/volume')
def volume_details(test_id):
    name = request.args.get('volume_name')
    sess = _create_session(test_id)
    cinder = cinder_cli.Client(CINDER_VERSION, session=sess)
    volumes = cinder.volumes.list(search_opts={"name": name})
    if volumes and len(volumes):
        volume = volumes[0]
        return jsonify(_volume_to_json(volume))
    else:
        abort(404)