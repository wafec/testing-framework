package robtest.stateinterfw.faults.operators.booleans;

import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.faults.operators.AbstractMutator;

public abstract class AbstractBooleanMutator extends AbstractMutator {
    public AbstractBooleanMutator(String key) {
        super(key);
    }

    @Override
    public String getCategory() {
        return "boolean";
    }

    @Override
    public String mutate(String value) {
        if (StringUtils.isEmpty(value) && !allowEmpty()) {
            throw new IllegalArgumentException("Cannot be empty");
        }
        return mutateInternal(value);
    }
}
