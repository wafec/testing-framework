package robtest.stateinterfw.faults.operators.text;

import org.apache.commons.lang3.RandomStringUtils;

public class AlphanumericTextMutator extends AbstractTextMutator {
    private int _count;

    public AlphanumericTextMutator() {
        this(100);
    }

    public AlphanumericTextMutator(int count) {
        super("text-alpha");
        _count = count;
    }

    @Override
    protected String mutateInternal(String value) {
        return RandomStringUtils.randomAlphanumeric(_count);
    }
}
