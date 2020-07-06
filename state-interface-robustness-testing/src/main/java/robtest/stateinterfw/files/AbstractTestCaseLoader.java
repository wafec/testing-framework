package robtest.stateinterfw.files;

import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.files.mapper.IFileMapper;

public abstract class AbstractTestCaseLoader implements ITestCaseLoader {
    protected final IFileMapper _fileMapper;
    protected final IRepository _repository;

    protected AbstractTestCaseLoader(IFileMapper fileMapper, IRepository repository) {
        this._fileMapper = fileMapper;
        this._repository = repository;
    }

    @Override
    public void add(IFileTestCase fileTestCase) {
        ITestCase testCase = _fileMapper.map(fileTestCase, TestCase.class);
        _repository.save(testCase);
    }
}
