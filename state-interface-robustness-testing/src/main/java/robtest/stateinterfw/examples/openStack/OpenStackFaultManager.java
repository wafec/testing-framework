package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.FaultManager;
import robtest.stateinterfw.IMutantTestCaseFactory;
import robtest.stateinterfw.data.IRepository;

public class OpenStackFaultManager extends FaultManager implements IOpenStackFaultManager {
    @Inject
    public OpenStackFaultManager(IOpenStackMutatorCatalog mutatorCatalog,
                                 IMutantTestCaseFactory mutantTestCaseFactory,
                                 IRepository repository) {
        super(mutatorCatalog, mutantTestCaseFactory, null, repository);
    }
}
