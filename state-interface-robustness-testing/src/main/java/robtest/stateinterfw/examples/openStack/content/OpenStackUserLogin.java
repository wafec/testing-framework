package robtest.stateinterfw.examples.openStack.content;

public class OpenStackUserLogin {
    private String endpoint;
    private String user;
    private String password;
    private String domain;
    private String project;

    public OpenStackUserLogin() {

    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProject() {
        return this.project;
    }
}
