package robtest.stateinterfw.examples.openStack.content;

public class OpenStackUserAction {
    private int id;
    private String uid;
    private OpenStackUserResource resource;
    private String action;
    private String details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public OpenStackUserResource getResource() {
        return resource;
    }

    public void setResource(OpenStackUserResource resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
