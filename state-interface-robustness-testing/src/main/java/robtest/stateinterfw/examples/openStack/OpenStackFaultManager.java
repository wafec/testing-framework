package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.FaultManager;
import robtest.stateinterfw.ITestCaseFaultFactory;
import robtest.stateinterfw.data.IRepository;

public class OpenStackFaultManager extends FaultManager implements IOpenStackFaultManager {
    @Inject
    public OpenStackFaultManager(IOpenStackMutatorCatalog mutatorCatalog,
                                 ITestCaseFaultFactory mutantTestCaseFactory,
                                 IOpenStackFaultMutatorFilter mutatorFilter,
                                 IRepository repository) {
        super(mutatorCatalog, mutantTestCaseFactory, mutatorFilter, repository);
    }
}
