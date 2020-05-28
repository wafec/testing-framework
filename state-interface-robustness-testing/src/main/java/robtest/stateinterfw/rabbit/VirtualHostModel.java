package robtest.stateinterfw.rabbit;

public class VirtualHostModel implements IVirtualHostModel {
    private String name;

    public VirtualHostModel() {

    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
