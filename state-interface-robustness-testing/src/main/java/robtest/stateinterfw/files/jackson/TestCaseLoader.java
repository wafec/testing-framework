package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.files.mapper.IFileMapper;

import java.io.File;
import java.io.IOException;

public class TestCaseLoader implements ITestCaseLoader {
    private IRepository _repository;
    private IFileMapper _fileMapper;

    @Inject
    public TestCaseLoader(IRepository repository, IFileMapper fileMapper) {
        _repository = repository;
        _fileMapper = fileMapper;
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

    @Override
    public void add(IFileTestCase fileTestCase) {
        ITestCase testCase = _fileMapper.map(fileTestCase, TestCase.class);
        _repository.save(testCase);
    }
}
