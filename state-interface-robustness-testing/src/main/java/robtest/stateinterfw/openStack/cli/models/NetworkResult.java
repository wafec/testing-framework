package robtest.stateinterfw.openStack.cli.models;

public class NetworkResult {
    private String id;
    private String name;
    private String project;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return String.format("<OSNetwork(id=%s, name=%s, project=%s)>", id, name, project);
    }
}
