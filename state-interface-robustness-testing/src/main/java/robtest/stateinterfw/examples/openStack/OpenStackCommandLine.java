package robtest.stateinterfw.examples.openStack;

public class OpenStackCommandLine implements IOpenStackCommandLine {
    private IOpenStackTestManager _openStackTestManager;

    public OpenStackCommandLine(IOpenStackTestManager openStackTestManager) {
        this._openStackTestManager = openStackTestManager;
    }

    @Override
    public void run(String... args) {

    }
}
