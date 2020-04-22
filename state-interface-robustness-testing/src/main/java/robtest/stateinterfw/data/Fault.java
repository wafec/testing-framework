package robtest.stateinterfw.data;

import robtest.stateinterfw.FrameContext;
import robtest.stateinterfw.IFault;
import robtest.stateinterfw.IMutator;

public class Fault implements IFault {
    private int id;
    private String name;
    private String targetDataType;
    private String category;

    public Fault() {

    }

    public Fault(String name, String targetDataType, String category) {
        this.name = name;
        this.targetDataType = targetDataType;
        this.category = category;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTargetDataType() {
        return targetDataType;
    }

    public void setTargetDataType(String targetDataType) {
        this.targetDataType = targetDataType;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String apply(String value) {
        IMutator mutator = FrameContext.Mutator.current().findOne(getFaultKey());
        if (mutator != null) {
            return mutator.mutate(value);
        } else
            throw new UnsupportedOperationException();
    }

    @Override
    public String getFaultKey() {
        return String.format("%s.%s.%s", getCategory(), getTargetDataType(), getName());
    }
}
