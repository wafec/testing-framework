package robtest.stateinterfw.web.models;

public class TestInputArgsCreateModel {
    private Integer inputId;
    private String name;
    private String dataType;
    private String dataValue;
    private Integer argId;
    private String operation;

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

    public void setArgId(Integer argId) {
        this.argId = argId;
    }

    public Integer getArgId() {
        return argId;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
