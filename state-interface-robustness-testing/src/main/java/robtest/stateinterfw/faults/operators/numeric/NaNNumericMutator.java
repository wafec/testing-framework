package robtest.stateinterfw.faults.operators.numeric;

public class NaNNumericMutator extends AbstractNumericMutator {
    public NaNNumericMutator() {
        super("number-NaN");
    }

    @Override
    protected String mutateInternal(String value) {
        return "NaN";
    }
}
