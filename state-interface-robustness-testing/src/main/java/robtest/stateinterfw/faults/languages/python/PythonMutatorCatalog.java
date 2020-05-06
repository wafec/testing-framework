package robtest.stateinterfw.faults.languages.python;

import com.google.inject.Inject;
import robtest.stateinterfw.faults.DefaultMutatorCatalog;
import robtest.stateinterfw.faults.languages.python.booleans.IPyBooleanConvert;
import robtest.stateinterfw.faults.languages.python.booleans.PyFalseBooleanMutator;
import robtest.stateinterfw.faults.languages.python.booleans.PyNegationBooleanMutator;
import robtest.stateinterfw.faults.languages.python.booleans.PyTrueBooleanMutator;
import robtest.stateinterfw.faults.languages.python.numeric.IPyNumericConvert;
import robtest.stateinterfw.faults.languages.python.numeric.PyNullNumericMutator;
import robtest.stateinterfw.faults.languages.python.text.IPyTextConvert;
import robtest.stateinterfw.faults.languages.python.text.PyNullTextMutator;

public class PythonMutatorCatalog extends DefaultMutatorCatalog implements IPythonMutatorCatalog {
    private IPyBooleanConvert _booleanConverter;
    private IPyTextConvert _textConverter;
    private IPyNumericConvert _numericConverter;

    @Inject
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
