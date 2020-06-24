package robtest.core;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ConditionalWaiter {
    private ConditionalWaiter() { }

    public static ConditionalWaiterResult waitFor(Supplier<Boolean> supplier, long timeout, long interval) {
        long start = System.currentTimeMillis();
        long end = start;
        boolean lastResult = false;
        while (!lastResult && (end - start) < timeout) {
            lastResult = supplier.get();
            end = System.currentTimeMillis();
            if (!lastResult)
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                    throw new TimeoutException(exc.getMessage());
                }
        }
        long timeSpent = end - start;
        return new ConditionalWaiterResult(timeSpent, lastResult, timeout);
    }
}
