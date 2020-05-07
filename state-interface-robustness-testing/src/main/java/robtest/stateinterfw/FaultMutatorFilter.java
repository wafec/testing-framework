package robtest.stateinterfw;

import com.google.inject.Inject;

public class FaultMutatorFilter implements IFaultMutatorFilter {
    private IMutatorMessageUtils _mutatorMessageUtils;

    public FaultMutatorFilter(IMutatorMessageUtils mutatorMessageUtils) {
        _mutatorMessageUtils = mutatorMessageUtils;
    }

    @Override
    public boolean canApply(IMutator mutator, String targetDataType) {
        return mutator.getCategory().equals(_mutatorMessageUtils.dataTypeToCategory(targetDataType));
    }
}
