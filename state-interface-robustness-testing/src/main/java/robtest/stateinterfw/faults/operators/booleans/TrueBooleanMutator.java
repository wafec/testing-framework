package robtest.stateinterfw.faults.operators.booleans;

public class TrueBooleanMutator extends AbstractBooleanMutator {
    public TrueBooleanMutator() {
        super("boolean-true");
    }

    @Override
    protected String mutateInternal(String value) {
        return "true";
    }
}
