package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestInput;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.files.AbstractTestCaseLoader;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.files.mapper.IFileMapper;

import java.io.File;
import java.io.IOException;

public class TestCaseLoader extends AbstractTestCaseLoader implements IJsonTestCaseLoader {
    @Inject
    public TestCaseLoader(IRepository repository, IFileMapper fileMapper) {
        super(fileMapper, repository);
    }

    @Override
    public IFileTestCase load(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        FileTestCase testCase = null;
        try {
            testCase = objectMapper.readValue(new File(path), FileTestCase.class);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return testCase;
    }
}
