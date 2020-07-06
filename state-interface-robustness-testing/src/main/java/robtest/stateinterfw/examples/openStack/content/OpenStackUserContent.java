package robtest.stateinterfw.examples.openStack.content;

import java.util.ArrayList;
import java.util.List;

public class OpenStackUserContent {
    private int id;
    private final OpenStackUserLogin login = new OpenStackUserLogin();
    private final List<OpenStackUserResource> resources = new ArrayList<>();
    private final List<OpenStackUserAction> actions = new ArrayList<>();

    public OpenStackUserContent() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public OpenStackUserLogin getLogin() {
        return this.login;
    }

    public void addResource(OpenStackUserResource resource) {
        resources.add(resource);
    }

    public void removeResource(OpenStackUserResource resource) {
        int index = resources.indexOf(resource);
        if (index >= 0)
            resources.remove(index);
    }

    public OpenStackUserResource findResource(String name, ResourceTypeEnum resourceType) {
        for (OpenStackUserResource resource : resources) {
            if (resource.getName().equals(name) &&
                    resource.getResourceType().equals(resourceType)) {
                return resource;
            }
        }
        return null;
    }

    public List<OpenStackUserResource> getResources() {
        return this.resources;
    }

    public void addAction(OpenStackUserAction action) {
        actions.add(action);
    }

    public void removeAction(OpenStackUserAction action) {
        int index = actions.indexOf(action);
        if (index >= 0)
            actions.remove(index);
    }

    public List<OpenStackUserAction> getActions() {
        return actions;
    }
}
