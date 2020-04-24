package robtest.stateinterfw.files;

import java.util.List;

public interface IFileTestInput {
    String getAction();
    boolean isLocked();
    List<IFileTestInputArgument> getArgs();
    List<IFileTestState> getStates();
}
