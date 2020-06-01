package robtest.core.commandline;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractCommandLine {
    private final Map<String, Consumer<String[]>> consumers = new HashMap<>();

    protected void add(String command, Consumer<String[]> consumer) {
        consumers.put(command, consumer);
    }

    protected void remove(String command) {
        consumers.remove(command);
    }

    protected void parse(String[] args) {
        if (args != null && args.length >= 1) {
            String command = args[0];
            if (consumers.containsKey(command)) {
                var consumer = consumers.get(command);
                consumer.accept(Arrays.copyOfRange(args, 1, args.length));
            } else {
                throw new IllegalArgumentException(String.format("%s not found", command));
            }
        } else {
            throw new IllegalArgumentException("Args is null or empty");
        }
    }
}
