package robtest.stateinterfw.faults.operators.numeric;

public class UnderflowNumericMutator extends AbstractNumericMutator {
    public UnderflowNumericMutator() {
        super("number-underflow");
    }

    @Override
    protected String mutateInternal(String value) {
        Long l = Long.MIN_VALUE;
        return l.toString();
    }
}
