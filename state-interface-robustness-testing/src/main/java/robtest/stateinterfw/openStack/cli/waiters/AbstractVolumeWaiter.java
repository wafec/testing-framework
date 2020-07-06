package robtest.stateinterfw.openStack.cli.waiters;

import robtest.core.ConditionalWaiter;
import robtest.core.ConditionalWaiterResult;
import robtest.stateinterfw.openStack.cli.ClientException;
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

    protected boolean evaluate(ClientException clientException) {
        throw clientException;
    }

                               @Override
    public ConditionalWaiterResult waitCondition() {
        final VolumeClient volumeClient = new VolumeClient();
        return ConditionalWaiter.waitFor(() -> {
            try {
                final var volumeResult = volumeClient.volumeDetails(testId, volumeName);
                if (volumeResult != null)
                    System.out.println(volumeResult.toString());
                return evaluate(volumeResult);
            } catch (ClientException clientException) {
                return evaluate(clientException);
            }
        }, timeout, interval);
    }
}
