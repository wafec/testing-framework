package robtest.stateinterfw.faults.operators.numeric;

public class PlusOneNumericMutator extends AbstractNumericMutator {
    public PlusOneNumericMutator() {
        super("number-plus-one");
    }

    @Override
    protected String mutateInternal(String value) {
        Double d = Double.parseDouble(value);
        d = d - 1;
        return d.toString();
    }

    @Override
    public boolean allowEmpty() {
        return false;
    }
}
