package robtest.stateinterfw.faults.operators.text;

import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.faults.operators.AbstractMutator;

public abstract class AbstractTextMutator extends AbstractMutator {
    public AbstractTextMutator(String key) {
        super(key);
    }

    @Override
    public String mutate(String value) {
        if (value == null || StringUtils.isNotEmpty(value)) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        return mutateInternal(value);
    }

    @Override
    public String getCategory() {
        return "text";
    }
}
