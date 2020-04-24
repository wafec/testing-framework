package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import robtest.stateinterfw.files.IFileTestInputArgument;

public class FileTestInputArgument implements IFileTestInputArgument {
    private String name;
    private String dataType;
    private String dataValue;

    public FileTestInputArgument() {

    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    @Override
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @JsonProperty("value")
    @Override
    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;

    }
}
