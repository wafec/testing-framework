package robtest.stateinterfw.files;

public interface ITestCaseLoader {
    IFileTestCase load(String path);
    void add(IFileTestCase fileTestCase);
}
