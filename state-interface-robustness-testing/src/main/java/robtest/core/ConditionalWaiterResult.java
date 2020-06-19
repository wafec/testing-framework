package robtest.core;

public class ConditionalWaiterResult {
    private long timeSpent;
    private boolean value;

    public ConditionalWaiterResult(long timeSpent, boolean value) {
        this.timeSpent = timeSpent;
        this.value = value;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public boolean get() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("<ConditionalWaiterResult(timeSpent=%d, value=%b)>", timeSpent, value);
    }
}
