package robtest.stateinterfw.data;

import java.util.ArrayList;
import java.util.List;

public class Param implements IParam {
    private Param prev;
    private String name;
    private Object value;

    private Param(String name, Object value) {
        this(null, name, value);
    }

    private Param(Param prev, String name, Object value) {
        this.prev = prev;
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public static Param list(String name, Object value) {
        return new Param(name, value);
    }

    public Param add(String name, Object value) {
        return new Param(this, name, value);
    }

    public IParam[] all() {
        List<Param> result = new ArrayList<>();
        Param prev = this.prev;
        result.add(this);
        while (prev != null) {
            result.add(prev);
            prev = ((Param) prev).prev;
        }
        return result.toArray(new IParam[result.size()]);
    }
}
