package robtest.stateinterfw.faults.operators.text;

import org.apache.commons.lang3.RandomStringUtils;

public class PlusOneTextMutator extends AbstractTextMutator {
    public PlusOneTextMutator() {
        super("text-plus-one");
    }

    @Override
    protected String mutateInternal(String value) {
        return value + RandomStringUtils.randomAscii(1);
    }

    @Override
    protected boolean allowEmpty() {
        return false;
    }
}
