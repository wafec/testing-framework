package robtest.stateinterfw.faults.languages.python.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.faults.languages.python.IPythonMutatorCatalog;
import robtest.stateinterfw.faults.languages.python.PythonMutatorCatalog;
import robtest.stateinterfw.faults.languages.python.booleans.IPyBooleanConvert;
import robtest.stateinterfw.faults.languages.python.booleans.PyBooleanConvert;
import robtest.stateinterfw.faults.languages.python.numeric.IPyNumericConvert;
import robtest.stateinterfw.faults.languages.python.numeric.PyNumericConvert;
import robtest.stateinterfw.faults.languages.python.text.IPyTextConvert;
import robtest.stateinterfw.faults.languages.python.text.PyTextConvert;

public class PythonModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IPythonMutatorCatalog.class).to(PythonMutatorCatalog.class);
        bind(IPyBooleanConvert.class).to(PyBooleanConvert.class);
        bind(IPyNumericConvert.class).to(PyNumericConvert.class);
        bind(IPyTextConvert.class).to(PyTextConvert.class);
    }
}
