package robtest.stateinterfw.faults.languages.python.numeric;

import robtest.stateinterfw.faults.operators.numeric.NullNumericMutator;

public class PyNullNumericMutator extends NullNumericMutator {
    private IPyNumericConvert converter;

    public PyNullNumericMutator(IPyNumericConvert converter) {
        super();
        this.converter = converter;
    }

    @Override
    public String mutateInternal(String value) {
        return converter.convert(value);
    }
}
