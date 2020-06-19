package robtest.stateinterfw.openStack.cli.models;

public class FlavorResult {
    private String id;
    private String name;
    private Integer ram;
    private Integer vcpus;
    private Integer disk;

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

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getVcpus() {
        return vcpus;
    }

    public void setVcpus(Integer vcpus) {
        this.vcpus = vcpus;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    @Override
    public String toString() {
        return String.format("<Flavor(id=%s, name=%s, ram=%d, vcpus=%d, disk=%d)>", id, name, ram, vcpus, disk);
    }
}
