package robtest.stateinterfw;

import robtest.core.IFunction;
import robtest.stateinterfw.data.TestStateCollection;
import robtest.stateinterfw.data.TestStateVerdictItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class TestStateCommand implements ITestStateCommand {
    private final Map<String, IFunction<ITestState, ITestInputArgs, ITestStateVerdictItem>> states =
            new HashMap<>();
    protected ITestExecutionContext context;

    protected void add(String stateText, IFunction<ITestState, ITestInputArgs, ITestStateVerdictItem> stateFunc) {
        states.put(stateText, stateFunc);
    }

    @Override
    public ITestStateVerdict command(ITestExecutionContext testExecutionContext, ITestInput testInput) {
        this.context = testExecutionContext;
        TestStateVerdict verdict = new TestStateVerdict();
        if (testInput.getStates() != null) {
            ITestStateCollection statesCollection = testInput.getStates();
            for (var i = 0; i < statesCollection.size(); i++) {
                ITestState testState = statesCollection.get(i);
                if (states.containsKey(testState.getState().getName())) {
                    try {
                        var stateFunc = states.get(testState.getState().getName());
                        var item = stateFunc.apply(testState, testInput.getArgs());
                        verdict.add(item);
                    } catch (Exception exc) {
                        var item = TestStateVerdictItem.createExceptionError(testState, exc);
                        verdict.add(item);
                    }
                }
            }
        }
        return verdict;
    }
}
