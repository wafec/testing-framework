package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import robtest.stateinterfw.files.IFileTestInput;
import robtest.stateinterfw.files.IFileTestInputArgument;
import robtest.stateinterfw.files.IFileTestState;

import java.util.List;
import java.util.stream.Collectors;

public class FileTestInput implements IFileTestInput {
    private String action;
    private boolean locked;
    private List<FileTestInputArgument> arguments;
    private List<FileTestState> fileStates;

    @Override
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @JsonGetter("locked")
    @Override
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @JsonIgnore
    @Override
    public List<IFileTestInputArgument> getArgs() {
        return arguments.stream().map(a -> (IFileTestInputArgument) a).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public List<IFileTestState> getStates() {
        return fileStates.stream().map(s -> (IFileTestState) s).collect(Collectors.toList());
    }

    @JsonProperty("states")
    public void setFileStates(List<FileTestState> states) {
        this.fileStates = states;
    }

    @JsonProperty("states")
    public List<FileTestState> getFileStates() {
        return this.fileStates;
    }

    @JsonProperty("args")
    public void setArguments(List<FileTestInputArgument> arguments) {
        this.arguments = arguments;
    }

    @JsonProperty("args")
    public List<FileTestInputArgument> getArguments() {
        return this.arguments;
    }
}
