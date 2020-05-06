package robtest.stateinterfw.faults.languages.python.booleans;

import org.apache.commons.lang3.StringUtils;

public class PyBooleanConvert implements IPyBooleanConvert {
    @Override
    public String convert(String value) {
        if (StringUtils.isEmpty(value))
            return "None";
        switch (value) {
            case "true": return "True";
            case "false": return "False";
        }
        throw new IllegalArgumentException("Not a boolean");
    }
}
