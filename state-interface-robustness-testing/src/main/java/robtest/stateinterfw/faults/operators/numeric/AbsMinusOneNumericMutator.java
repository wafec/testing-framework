package robtest.stateinterfw.faults.operators.numeric;

public class AbsMinusOneNumericMutator extends AbstractNumericMutator {
    public AbsMinusOneNumericMutator() {
        super("number-abs-minus-one");
    }

    @Override
    protected String mutateInternal(String value) {
        return "-1";
    }
}
