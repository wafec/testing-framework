package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;

import java.io.File;
import java.io.IOException;

public class TestCaseLoader implements ITestCaseLoader {
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
