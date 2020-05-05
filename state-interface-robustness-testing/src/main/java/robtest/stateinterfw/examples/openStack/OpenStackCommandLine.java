package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;

public class OpenStackCommandLine implements IOpenStackCommandLine {
    private IOpenStackTestManager _openStackTestManager;

    @Inject
    public OpenStackCommandLine(IOpenStackTestManager openStackTestManager) {
        this._openStackTestManager = openStackTestManager;
    }

    @Override
    public void run(String... args) {
        System.out.println("Working");
    }
}
