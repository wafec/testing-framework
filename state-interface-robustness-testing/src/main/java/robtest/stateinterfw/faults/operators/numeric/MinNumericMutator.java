package robtest.stateinterfw.faults.operators.numeric;

public class MinNumericMutator extends AbstractNumericMutator {
    public MinNumericMutator() {
        super("number-min");
    }

    @Override
    protected String mutateInternal(String value) {
        return String.format("%d", Integer.MIN_VALUE);
    }
}
