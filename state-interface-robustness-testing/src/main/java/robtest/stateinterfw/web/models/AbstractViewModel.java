package robtest.stateinterfw.web.models;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractViewModel {
    private Map<String, Integer> indexes;

    public AbstractViewModel(String[] fields) {
        if (fields != null) {
            indexes = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                indexes.put(fields[i], (Integer) i);
            }
        }
    }

    protected <T> T get(String field, Object[] values) {
        if (indexes != null && indexes.containsKey(field)) {
            return (T) values[indexes.get(field)];
        }
        throw new UnsupportedOperationException(String.format("%s not found", field));
    }
}
