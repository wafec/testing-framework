package robtest.stateinterfw.faults.operators.numeric;

public class MaxNumericMutator extends AbstractNumericMutator {
    public MaxNumericMutator() {
        super("number-max");
    }

    @Override
    protected String mutateInternal(String value) {
        return String.format("%d", Integer.MAX_VALUE);
    }
}
