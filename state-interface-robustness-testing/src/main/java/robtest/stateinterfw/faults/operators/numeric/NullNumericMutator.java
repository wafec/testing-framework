package robtest.stateinterfw.faults.operators.numeric;

public class NullNumericMutator extends AbstractNumericMutator {
    public NullNumericMutator() {
        super("number-null");
    }

    @Override
    protected String mutateInternal(String value) {
        return "null";
    }
}
