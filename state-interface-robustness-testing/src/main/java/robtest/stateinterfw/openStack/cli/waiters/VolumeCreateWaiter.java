package robtest.stateinterfw.openStack.cli.waiters;

import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.openStack.cli.models.VolumeResult;

import java.util.Optional;

public class VolumeCreateWaiter extends AbstractVolumeWaiter {
    public VolumeCreateWaiter(int testId, String volumeName) {
        super(testId, volumeName);
    }

    @Override
    protected boolean evaluate(VolumeResult volumeResult) {
        return Optional.ofNullable(volumeResult).map(VolumeResult::getStatus).map(StringUtils::lowerCase)
                .map(s -> s.equals("available")).orElse(false);
    }
}
