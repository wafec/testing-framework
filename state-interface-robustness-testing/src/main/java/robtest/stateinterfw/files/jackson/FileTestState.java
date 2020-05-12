package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.annotation.JsonGetter;
import robtest.stateinterfw.files.IFileTestState;

public class FileTestState implements IFileTestState {
    private int order;
    private int stateId;

    public FileTestState() {

    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public int getStateId() {
        return stateId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
}
