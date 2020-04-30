package robtest.stateinterfw.faults.operators.numeric;

public class MinusOneNumericMutator extends AbstractNumericMutator {
    public MinusOneNumericMutator() {
        super("number-minus-one");
    }

    @Override
    protected String mutateInternal(String value) {
        Double d = Double.parseDouble(value);
        d = d + 1;
        return d.toString();
    }

    @Override
    protected boolean allowEmpty() {
        return false;
    }
}
