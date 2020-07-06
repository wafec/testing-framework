package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.VolumeResult;

import java.util.Optional;

public class VolumeDetachWaiter extends AbstractVolumeWaiter {
    public VolumeDetachWaiter(int testId, String volumeName) {
        super(testId, volumeName);
    }

    @Override
    protected boolean evaluate(VolumeResult volumeResult) {
        return Optional.of(volumeResult).map(VolumeResult::getStatus).map(String::toLowerCase)
                .map(s -> s.equals("available")).orElse(false);
    }
}
