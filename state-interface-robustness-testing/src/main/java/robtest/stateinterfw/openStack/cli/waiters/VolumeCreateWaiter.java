package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.VolumeResult;

public class VolumeCreateWaiter extends AbstractVolumeWaiter {
    public VolumeCreateWaiter(int testId, String volumeName) {
        super(testId, volumeName);
    }

    @Override
    protected boolean evaluate(VolumeResult volumeResult) {
        System.out.println(volumeResult.toString());
        return false;
    }
}
