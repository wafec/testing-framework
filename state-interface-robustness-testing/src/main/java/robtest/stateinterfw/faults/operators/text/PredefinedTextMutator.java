package robtest.stateinterfw.faults.operators.text;

import org.apache.commons.lang3.RandomStringUtils;

public class PredefinedTextMutator extends AbstractTextMutator {
    private int _count;

    public PredefinedTextMutator() {
        this(100);
    }

    public PredefinedTextMutator(int count) {
        super("text-predefined");
        _count = count;
    }

    @Override
    protected String mutateInternal(String value) {
        return RandomStringUtils.randomAscii(_count);
    }
}
