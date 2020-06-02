package robtest.stateinterfw;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class TestInputCommand implements ITestInputCommand {
    private final Map<String, Function<ITestInputArgs, Object>> actions = new HashMap<>();
    protected ITestExecutionContext testExecutionContext;

    protected void add(String action, Function<ITestInputArgs, Object> actionFunc) {
        actions.put(action, actionFunc);
    }

    protected void remove(String action) {
        actions.remove(action);
    }

    @Override
    public Object command(ITestExecutionContext testExecutionContext, ITestInput testInput) {
        this.testExecutionContext = testExecutionContext;
        Object result = null;
        if (actions.containsKey(testInput.getAction())) {
            var actionFunc = actions.get(testInput.getAction());
            result = actionFunc.apply(testInput.getArgs());
        }
        return result;
    }
}
