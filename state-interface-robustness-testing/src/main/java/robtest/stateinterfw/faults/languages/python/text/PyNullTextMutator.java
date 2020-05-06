package robtest.stateinterfw.faults.languages.python.text;

import robtest.stateinterfw.faults.operators.text.NullTextMutator;

public class PyNullTextMutator extends NullTextMutator {
    private IPyTextConvert converter;

    public PyNullTextMutator(IPyTextConvert converter) {
        super();
        this.converter = converter;
    }

    @Override
    public String mutateInternal(String value) {
        return converter.convert(value);
    }
}
