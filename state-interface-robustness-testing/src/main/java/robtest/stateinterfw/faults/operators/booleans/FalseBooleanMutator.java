package robtest.stateinterfw.faults.operators.booleans;

public class FalseBooleanMutator extends AbstractBooleanMutator {
    public FalseBooleanMutator() {
        super("boolean-false");
    }

    @Override
    protected String mutateInternal(String value) {
        return "false";
    }
}
