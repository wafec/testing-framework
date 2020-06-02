package robtest.stateinterfw.examples.openStack.content;

public class OpenStackUserContent {
    private final OpenStackUserLogin login = new OpenStackUserLogin();

    public OpenStackUserContent() {

    }

    public OpenStackUserLogin getLogin() {
        return this.login;
    }
}
