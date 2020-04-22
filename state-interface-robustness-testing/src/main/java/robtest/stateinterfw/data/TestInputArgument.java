package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestInputArgument;

public class TestInputArgument implements ITestInputArgument {
    private int id;
    private String name;
    private String dataType;
    private String dataValue;
    private TestInput testInput;

    public TestInputArgument() {

    }

    public TestInputArgument(String name, String dataType, String dataValue) {
        this.name = name;
        this.dataType = dataType;
        this.dataValue = dataValue;
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
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public void setTestInput(TestInput testInput) {
        this.testInput = testInput;
    }

    public TestInput getTestInput() {
        return this.testInput;
    }
}
