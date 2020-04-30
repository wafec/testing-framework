package robtest.stateinterfw.faults.operators.text;

import org.apache.commons.lang3.RandomStringUtils;

public class PlusPredefinedTextMutator extends AbstractTextMutator {
    private int _count;

    public PlusPredefinedTextMutator() {
        this(100);
    }

    public PlusPredefinedTextMutator(int count) {
        super("text-plus-predefined");
        _count = count;
    }

    @Override
    protected String mutateInternal(String value) {
        return value + RandomStringUtils.randomAscii(_count);
    }

    @Override
    protected boolean allowEmpty() {
        return false;
    }
}
