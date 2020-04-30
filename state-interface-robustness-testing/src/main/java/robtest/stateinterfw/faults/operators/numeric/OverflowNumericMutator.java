package robtest.stateinterfw.faults.operators.numeric;

public class OverflowNumericMutator extends AbstractNumericMutator {
    public OverflowNumericMutator() {
        super("number-overflow");
    }

    @Override
    protected String mutateInternal(String value) {
        Long l = Long.MAX_VALUE;
        return l.toString();
    }
}
