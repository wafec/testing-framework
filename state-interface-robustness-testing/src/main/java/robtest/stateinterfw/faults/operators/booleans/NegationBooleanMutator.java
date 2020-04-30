package robtest.stateinterfw.faults.operators.booleans;

public class NegationBooleanMutator extends AbstractBooleanMutator {

    public NegationBooleanMutator() {
        super("boolean-negation");
    }
    @Override
    protected String mutateInternal(String value) {
        Boolean b = Boolean.parseBoolean(value);
        b = !b;
        return b.toString();
    }
}
