package robtest.stateinterfw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestStateVerdict implements ITestStateVerdict {
    private List<ITestStateVerdictItem> items = new ArrayList<>();

    public void setItems(List<ITestStateVerdictItem> items) {
        this.items = items;
    }

    @Override
    public boolean passOk() {
        return this.items.stream().anyMatch(ITestStateVerdictItem::hasError);
    }

    @Override
    public List<String> getErrorReasons() {
        return this.items.stream().map(ITestStateVerdictItem::getErrorMessage).collect(Collectors.toList());
    }

    @Override
    public List<ITestStateVerdictItem> getItems() {
        return this.items;
    }

    public void add(ITestStateVerdictItem item) {
        this.items.add(item);
    }

    public void remove(int index) {
        this.items.remove(index);
    }

    public int size() {
        return items.size();
    }
}
