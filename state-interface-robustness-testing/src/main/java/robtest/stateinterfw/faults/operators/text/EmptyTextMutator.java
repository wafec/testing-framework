package robtest.stateinterfw.faults.operators.text;

public class EmptyTextMutator extends AbstractTextMutator {
    public EmptyTextMutator() {
        super("text-empty");
    }

    @Override
    protected String mutateInternal(String value) {
        return "";
    }
}
