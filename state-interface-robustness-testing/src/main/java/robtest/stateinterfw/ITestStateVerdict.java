package robtest.stateinterfw;

import java.util.List;

public interface ITestStateVerdict {
    boolean passOk();
    List<String> getErrorReasons();
    List<ITestStateVerdictItem> getItems();
}
