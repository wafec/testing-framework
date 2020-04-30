package robtest.stateinterfw.faults.operators.numeric;

public class AbsPlusOneNumericMutator extends AbstractNumericMutator {
    public AbsPlusOneNumericMutator() {
        super("number-abs-plus-one");
    }

    @Override
    protected String mutateInternal(String value) {
        return "1";
    }
}
