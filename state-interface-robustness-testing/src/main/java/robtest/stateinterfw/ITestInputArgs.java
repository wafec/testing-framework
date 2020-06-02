package robtest.stateinterfw;

public interface ITestInputArgs extends IArgs<ITestInputArgument> {
    ITestInputArgument get(String name);
}
