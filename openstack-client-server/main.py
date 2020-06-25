from flask import Flask
from novaclient import client as nova_cli
from glanceclient import client as glance_cli
from glanceclient.exc import ClientException as glance_cli_exc
from neutronclient.v2_0 import client as neutron_cli
import traceback
from commons import _make_error
from servers import servers_api
from storage import storage_api

app = Flask(__name__)

app.register_blueprint(servers_api, url_prefix='/servers_api')
app.register_blueprint(storage_api, url_prefix='/storage_api')


@app.route('/')
def hello():
    return "Hello World!"


@servers_api.errorhandler(nova_cli.exceptions.ClientException)
def error_handler_nova(e):
    return _make_error(402, 11, str(e), traceback.format_exc())


@servers_api.errorhandler(glance_cli_exc)
def error_handler_glance(e):
    return _make_error(403, 12, str(e), traceback.format_exc())


@servers_api.errorhandler(neutron_cli.exceptions.NeutronClientException)
def error_handler_neutron(e):
    return _make_error(404, 13, str(e), traceback.format_exc())


@servers_api.errorhandler(Exception)
def error_handler_any(e):
    return _make_error(401, 10, str(e), traceback.format_exc())


if __name__ == "__main__":
    app.run()
