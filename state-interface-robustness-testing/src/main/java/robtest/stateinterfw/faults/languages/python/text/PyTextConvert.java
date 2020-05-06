package robtest.stateinterfw.faults.languages.python.text;

import org.apache.commons.lang3.StringUtils;

public class PyTextConvert implements IPyTextConvert {
    @Override
    public String convert(String value) {
        if (StringUtils.isEmpty(value))
            return null;
        switch (value) {
            case "null": return "None";
        }
        return value;
    }
}
