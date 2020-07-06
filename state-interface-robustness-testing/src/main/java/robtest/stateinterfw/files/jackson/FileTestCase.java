package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.IFileTestInput;

import java.util.List;

public class FileTestCase implements IFileTestCase {
    private int id;
    private String uniqueIdentifier;
    private List<FileTestInput> fileInputs;

    @Override
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public IFileTestInput get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        return fileInputs.get(index);
    }

    @Override
    public int size() {
        if (fileInputs == null)
            throw new NullPointerException();
        return fileInputs.size();
    }

    @Override
    public IFileTestInput getLast() {
        if (fileInputs.size() > 0)
            return fileInputs.get(fileInputs.size() - 1);
        return null;
    }

    @Override
    public IFileTestInput getFirst() {
        if (fileInputs.size() > 0)
            return fileInputs.get(0);
        return null;
    }

    @JsonProperty("inputs")
    public void setFileInputs(List<FileTestInput> fileInputs) {
        this.fileInputs = fileInputs;
    }

    @JsonProperty("inputs")
    public List<FileTestInput> getFileInputs() {
        return this.fileInputs;
    }
}
