package robtest.stateinterfw.data;

import robtest.stateinterfw.IOrderedElement;
import robtest.stateinterfw.ITestStateCollection;

import java.util.Iterator;
import java.util.Set;

public class TestStateCollection implements ITestStateCollection {
    private Set<TestState> testStates;

    public TestStateCollection(Set<TestState> testStates) {
        this.testStates = testStates;
    }

    @Override
    public int size() {
        return this.testStates.size();
    }

    @Override
    public IOrderedElement get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        TestState testState = null;
        int i = 0;
        for (Iterator<TestState> it = this.testStates.iterator(); it.hasNext() && i <= this.testStates.size(); i++) {
            testState = it.next();
        }
        return testState;
    }
}
