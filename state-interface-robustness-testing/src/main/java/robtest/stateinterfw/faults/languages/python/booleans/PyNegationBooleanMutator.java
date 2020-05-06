package robtest.stateinterfw.faults.languages.python.booleans;

import robtest.stateinterfw.faults.operators.booleans.NegationBooleanMutator;

public class PyNegationBooleanMutator extends NegationBooleanMutator {
    private IPyBooleanConvert converter;

    public PyNegationBooleanMutator(IPyBooleanConvert converter) {
        super();
        this.converter = converter;
    }

    @Override
    public String mutateInternal(String value) {
        return converter.convert(value);
    }
}
