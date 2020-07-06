package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.VolumeResult;

import java.util.Optional;

public class VolumeExtendWaiter extends AbstractVolumeWaiter {
    public VolumeExtendWaiter(int testId, String volumeName) {
        super(testId, volumeName);
    }

    @Override
    protected boolean evaluate(VolumeResult volumeResult) {
        return Optional.ofNullable(volumeResult).map(VolumeResult::getStatus).map(String::toLowerCase).equals(Optional.of("available"));
    }
}
