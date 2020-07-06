package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.ClientException;
import robtest.stateinterfw.openStack.cli.models.VolumeResult;

public class VolumeDeleteWaiter extends AbstractVolumeWaiter {
    public VolumeDeleteWaiter(int testId, String volumeName) {
        super(testId, volumeName);
    }

    @Override
    protected boolean evaluate(ClientException clientException) {
        return clientException.getCode() == 404;
    }

    @Override
    protected boolean evaluate(VolumeResult volumeResult) {
        return volumeResult == null;
    }
}
