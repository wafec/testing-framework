package robtest.stateinterfw.hbm;

import robtest.stateinterfw.IOrderedElement;
import robtest.stateinterfw.ITestMessageCollection;

import java.util.Iterator;
import java.util.Set;

public class TestMessageCollection implements ITestMessageCollection {
    private Set<TestMessage> testMessages;

    public TestMessageCollection(Set<TestMessage> testMessages) {
        this.testMessages = testMessages;
    }

    @Override
    public int size() {
        return testMessages.size();
    }

    @Override
    public IOrderedElement get(int index) {
        if (index <0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        TestMessage testMessage = null;
        int i = 0;
        for(Iterator<TestMessage> it = this.testMessages.iterator(); it.hasNext() && i <= index; i++) {
            testMessage = it.next();
        }
        return testMessage;
    }
}
