package robtest.stateinterfw.faults.languages.python.booleans;

import robtest.stateinterfw.faults.operators.booleans.TrueBooleanMutator;

public class PyTrueBooleanMutator extends TrueBooleanMutator {
    @Override
    public String mutateInternal(String value) {
        return "True";
    }
}
