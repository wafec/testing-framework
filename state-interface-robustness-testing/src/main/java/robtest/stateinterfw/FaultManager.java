package robtest.stateinterfw;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;

public abstract class FaultManager implements IFaultManager {
    private IMutatorCatalog _mutatorCatalog;
    private IMutantTestCaseFactory _mutantTestCaseFactory;
    private IFaultMutatorFilter _faultMutatorFilter;
    private IRepository _repository;

    public FaultManager(IMutatorCatalog mutatorCatalog,
                        IMutantTestCaseFactory mutantTestCaseFactory,
                        IFaultMutatorFilter faultMutatorFilter,
                        IRepository repository) {
        this._mutatorCatalog = mutatorCatalog;
        this._mutantTestCaseFactory = mutantTestCaseFactory;
        this._faultMutatorFilter = faultMutatorFilter;
        this._repository = repository;
    }

    @Override
    public ITestSuite generate(ITestCase testCase, IFaultStrategy faultStrategy) {
        IMutantTestCase mutantTestCase;
        ITestInput testInput = testCase.get(0);
        ITestMessage testMessage = testInput.getMessages().get(0);
        IMessage message = testMessage.getMessage();
        IMessageArgument argument = message.getArgs().get(0);
        _mutatorCatalog.filterBy(_faultMutatorFilter, argument.getDataType());
        return null;
    }
}
