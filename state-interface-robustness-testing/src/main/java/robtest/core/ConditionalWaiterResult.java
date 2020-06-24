package robtest.core;

public class ConditionalWaiterResult {
    private long timeSpent;
    private boolean value;
    private long timeout;

    public ConditionalWaiterResult(long timeSpent, boolean value, long timeout) {
        this.timeSpent = timeSpent;
        this.value = value;
        this.timeout = timeout;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public long getTimeout() { return timeout; }

    public boolean get() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("<ConditionalWaiterResult(timeSpent=%d, value=%b, timeout=%d)>", timeSpent, value, timeout);
    }
}
