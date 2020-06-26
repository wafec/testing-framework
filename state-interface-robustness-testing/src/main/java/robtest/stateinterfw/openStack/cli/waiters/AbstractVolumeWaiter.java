package robtest.stateinterfw.openStack.cli.waiters;

import robtest.core.ConditionalWaiter;
import robtest.core.ConditionalWaiterResult;
import robtest.stateinterfw.openStack.cli.VolumeClient;
import robtest.stateinterfw.openStack.cli.models.VolumeResult;

import java.util.concurrent.TimeUnit;

public abstract class AbstractVolumeWaiter implements IOSWaiter {
    protected int testId;
    protected String volumeName;
    private long timeout;
    private long interval;

    AbstractVolumeWaiter(int testId, String volumeName) {
        this(testId, volumeName, TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS), 100);
    }

    public AbstractVolumeWaiter(int testId, String volumeName, long timeout, long interval) {
        this.testId = testId;
        this.volumeName = volumeName;
        this.timeout = timeout;
        this.interval = interval;
    }

    protected abstract boolean evaluate(VolumeResult volumeResult);

    @Override
    public ConditionalWaiterResult waitCondition() {
        VolumeClient volumeClient = new VolumeClient();
        final var volumeResult = volumeClient.volumeDetails(testId, volumeName);
        return ConditionalWaiter.waitFor(() -> {
            return evaluate(volumeResult);
        }, timeout, interval);
    }
}
