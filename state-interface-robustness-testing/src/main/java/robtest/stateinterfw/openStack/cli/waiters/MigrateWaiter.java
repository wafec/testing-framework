package robtest.stateinterfw.openStack.cli.waiters;

public class MigrateWaiter extends ConfirmOrRejectResizeWaiter {
    public MigrateWaiter(int testId, String serverName) {
        super(testId, serverName);
    }
}
