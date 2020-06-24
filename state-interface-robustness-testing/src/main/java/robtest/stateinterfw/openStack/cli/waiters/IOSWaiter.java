package robtest.stateinterfw.openStack.cli.waiters;

import robtest.core.ConditionalWaiterResult;

public interface IOSWaiter {
    ConditionalWaiterResult waitCondition();
}
