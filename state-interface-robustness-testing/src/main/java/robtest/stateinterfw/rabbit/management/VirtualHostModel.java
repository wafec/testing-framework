package robtest.stateinterfw.rabbit.management;

import org.thymeleaf.util.StringUtils;
import robtest.stateinterfw.rabbit.management.IVirtualHostModel;

public class VirtualHostModel implements IVirtualHostModel {
    private String name;

    public VirtualHostModel() {

    }

    @Override
    public String getName() {
        if (isDefault())
            return "%2F";
        return name;
    }

    @Override
    public boolean isDefault() {
        return StringUtils.isEmpty(name) || name.equals("/");
    }

    public void setName(String name) {
        this.name = name;
    }
}
