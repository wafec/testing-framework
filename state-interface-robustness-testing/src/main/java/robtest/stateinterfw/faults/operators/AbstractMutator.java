package robtest.stateinterfw.faults.operators;

import robtest.stateinterfw.IMutator;

public abstract class AbstractMutator implements IMutator {
    private String _key;

    public AbstractMutator(String key) {
        _key = key;
    }

    @Override
    public String getKey() {
        return _key;
    }

    protected abstract String mutateInternal(String value);

    protected boolean allowEmpty() {
        return true;
    }
}
