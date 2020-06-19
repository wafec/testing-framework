package robtest.stateinterfw.data.openStack;

import robtest.stateinterfw.data.IEntity;

import java.util.Set;

public class OSTest implements IEntity {
    private int id;
    private String alias;
    private String username;
    private String password;
    private String projectName;
    private String userDomainName;
    private String projectDomainName;
    private String authUrl;

    private Set<OSImage> images;
    private Set<OSFlavor> flavors;
    private Set<OSNetwork> networks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserDomainName() {
        return userDomainName;
    }

    public void setUserDomainName(String userDomainName) {
        this.userDomainName = userDomainName;
    }

    public String getProjectDomainName() {
        return this.projectDomainName;
    }

    public void setProjectDomainName(String projectDomainName) {
        this.projectDomainName = projectDomainName;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public Set<OSFlavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(Set<OSFlavor> flavors) {
        this.flavors = flavors;
    }

    public Set<OSImage> getImages() {
        return images;
    }

    public void setImages(Set<OSImage> images) {
        this.images = images;
    }

    public Set<OSNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<OSNetwork> networks) {
        this.networks = networks;
    }
}
