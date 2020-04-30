package robtest.stateinterfw.faults.wrappers.python;

import robtest.stateinterfw.faults.DefaultMutatorCatalog;
import robtest.stateinterfw.faults.wrappers.python.booleans.IPyBooleanConvert;
import robtest.stateinterfw.faults.wrappers.python.booleans.PyFalseBooleanMutator;
import robtest.stateinterfw.faults.wrappers.python.booleans.PyNegationBooleanMutator;
import robtest.stateinterfw.faults.wrappers.python.booleans.PyTrueBooleanMutator;
import robtest.stateinterfw.faults.wrappers.python.numeric.IPyNumericConvert;
import robtest.stateinterfw.faults.wrappers.python.numeric.PyNullNumericMutator;
import robtest.stateinterfw.faults.wrappers.python.text.IPyTextConvert;
import robtest.stateinterfw.faults.wrappers.python.text.PyNullTextMutator;

public class PythonMutatorCatalog extends DefaultMutatorCatalog implements IPythonMutatorCatalog {
    private IPyBooleanConvert _booleanConverter;
    private IPyTextConvert _textConverter;
    private IPyNumericConvert _numericConverter;

    public PythonMutatorCatalog(IPyBooleanConvert booleanConverter,
                                IPyTextConvert textConverter,
                                IPyNumericConvert numericConverter) {
        super();
        _booleanConverter = booleanConverter;
        _textConverter = textConverter;
        _numericConverter = numericConverter;
        initialize();
    }

    private void initialize() {
        addPyMutators();
    }

    private void addPyMutators() {
        add(new PyFalseBooleanMutator());
        add(new PyTrueBooleanMutator());
        add(new PyNegationBooleanMutator(_booleanConverter));
        add(new PyNullTextMutator(_textConverter));
        add(new PyNullNumericMutator(_numericConverter));
    }
}
