package robtest.stateinterfw;

public interface ITestStateCommand {
    ITestStateVerdict command(ITestExecutionContext context, ITestInput testInput);
}
