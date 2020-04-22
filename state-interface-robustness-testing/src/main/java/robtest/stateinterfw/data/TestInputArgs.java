package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestInputArgs;
import robtest.stateinterfw.ITestInputArgument;

import java.util.Iterator;
import java.util.Set;

public class TestInputArgs implements ITestInputArgs {
    private Set<TestInputArgument> testInputArguments;

    public TestInputArgs(Set<TestInputArgument> testInputArguments) {
        this.testInputArguments = testInputArguments;
    }

    @Override
    public int size() {
        return this.testInputArguments.size();
    }

    @Override
    public ITestInputArgument get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ITestInputArgument testInputArgument = null;
        int i = 0;
        for (Iterator<TestInputArgument> it = this.testInputArguments.iterator(); it.hasNext() && i <= index; i++) {
            testInputArgument = it.next();
        }
        return testInputArgument;
    }
}
