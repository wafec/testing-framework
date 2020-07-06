package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.VolumeResult;

import java.util.Optional;

public class VolumeAttachWaiter extends AbstractVolumeWaiter {
    public VolumeAttachWaiter(int testId, String name) {
        super(testId, name);
    }

    @Override
    protected boolean evaluate(VolumeResult volumeResult) {
        return Optional.ofNullable(volumeResult).map(VolumeResult::getStatus).map(String::toLowerCase)
                .map(s -> s.equals("in-use")).orElse(false);
    }
}
