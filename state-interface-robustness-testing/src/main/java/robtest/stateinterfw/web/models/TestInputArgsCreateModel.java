package robtest.stateinterfw.web.models;

public class TestInputArgsCreateModel {
    private int inputId;
    private String name;
    private String dataType;
    private String dataValue;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setInputId(int inputId) {
        this.inputId = inputId;
    }

    public int getInputId() {
        return inputId;
    }
}
