package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.faults.languages.python.PythonMutatorCatalog;
import robtest.stateinterfw.faults.languages.python.booleans.IPyBooleanConvert;
import robtest.stateinterfw.faults.languages.python.numeric.IPyNumericConvert;
import robtest.stateinterfw.faults.languages.python.text.IPyTextConvert;

public class OpenStackMutatorCatalog extends PythonMutatorCatalog implements IOpenStackMutatorCatalog {
    @Inject
    public OpenStackMutatorCatalog(IPyBooleanConvert booleanConverter,
                                   IPyTextConvert textConverter,
                                   IPyNumericConvert numericConverter) {
        super(booleanConverter, textConverter, numericConverter);
    }
}
