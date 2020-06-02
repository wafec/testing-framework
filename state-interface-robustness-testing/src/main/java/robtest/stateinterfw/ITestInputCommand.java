package robtest.stateinterfw;

public interface ITestInputCommand {
    Object command(ITestExecutionContext testExecutionContext, ITestInput testInput);
}
