package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.FaultMutatorFilter;

public class OpenStackFaultMutatorFilter extends FaultMutatorFilter implements IOpenStackFaultMutatorFilter {
    @Inject
    public OpenStackFaultMutatorFilter(IOpenStackMutatorMessageUtils messageUtils) {
        super(messageUtils);
    }
}
