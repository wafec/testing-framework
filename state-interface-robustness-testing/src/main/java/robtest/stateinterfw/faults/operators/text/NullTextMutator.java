package robtest.stateinterfw.faults.operators.text;

public class NullTextMutator extends AbstractTextMutator {
    public NullTextMutator() {
        super("text-null");
    }

    @Override
    protected String mutateInternal(String value) {
        return "null";
    }
}
