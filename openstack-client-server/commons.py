from flask import jsonify
from models import OSTestLog, Session, OSTest
from keystoneauth1.identity import v3
from keystoneauth1 import session


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


def _create_test_log(sess, test_id, log):
    log_dao = OSTestLog()
    log_dao.test_id = int(test_id)
    log_dao.log = log

    sess.add(log_dao)

    return log_dao


def _create_session(test_id):
    sess = Session()
    test = sess.query(OSTest).filter(OSTest.id == int(test_id)).first()
    auth = v3.Password(auth_url=test.auth_url,
                       username=test.username,
                       password=test.password,
                       user_domain_name=test.user_domain_name,
                       project_name=test.project_name,
                       project_domain_name=test.project_domain_name)
    os_sess = session.Session(auth=auth)
    return os_sess
