package robtest.stateinterfw.faults.languages.python.numeric;

import org.apache.commons.lang3.StringUtils;

public class PyNumericConvert implements IPyNumericConvert {
    @Override
    public String convert(String value) {
        if (StringUtils.isEmpty(value))
            return "None";
        switch (value) {
            case "null": return "None";
        }
        return value;
    }
}
