package robtest.stateinterfw.faults.operators.numeric;

public class AbsNumericMutator extends AbstractNumericMutator {
    public AbsNumericMutator() {
        super("number-abs");
    }

    @Override
    protected String mutateInternal(String value) {
        return "0";
    }
}
