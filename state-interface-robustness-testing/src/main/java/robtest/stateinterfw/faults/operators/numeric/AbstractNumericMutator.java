package robtest.stateinterfw.faults.operators.numeric;

import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.faults.operators.AbstractMutator;

public abstract class AbstractNumericMutator extends AbstractMutator {
    public AbstractNumericMutator(String key) {
        super(key);
    }

    @Override
    public String mutate(String value) {
        if (StringUtils.isEmpty(value) && !allowEmpty()) {
            throw new IllegalArgumentException("Empty value not allowed");
        }
        if (StringUtils.isNumeric(value)) {
            String resValue = mutateInternal(value);
            if (StringUtils.isNotEmpty(value) && !(value.contains(".") || value.contains(","))) {
                int commaIndex = 0;
                if (value.contains("."))
                    commaIndex = resValue.indexOf(".");
                else
                    commaIndex = resValue.indexOf(",");
                return resValue.substring(0, commaIndex);
            } else {
                return resValue;
            }
        } else {
            throw new IllegalArgumentException(String.format("%s is not a valid number", value));
        }
    }

    @Override
    public String getCategory() {
        return "numeric";
    }
}
