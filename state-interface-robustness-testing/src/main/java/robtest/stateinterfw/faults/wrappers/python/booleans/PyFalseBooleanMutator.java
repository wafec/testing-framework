package robtest.stateinterfw.faults.wrappers.python.booleans;

import robtest.stateinterfw.faults.operators.booleans.FalseBooleanMutator;

public class PyFalseBooleanMutator extends FalseBooleanMutator {
    @Override
    public String mutateInternal(String value) {
        return "False";
    }
}
