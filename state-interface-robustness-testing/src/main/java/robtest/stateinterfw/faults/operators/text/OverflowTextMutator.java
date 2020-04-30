package robtest.stateinterfw.faults.operators.text;

public class OverflowTextMutator extends AbstractTextMutator {
    private int _threashold;

    public OverflowTextMutator() {
        this(15000);
    }

    public OverflowTextMutator(int threashold) {
        super("text-overflow");
        _threashold = threashold;
    }

    @Override
    protected String mutateInternal(String value) {
        return null;
    }
}
