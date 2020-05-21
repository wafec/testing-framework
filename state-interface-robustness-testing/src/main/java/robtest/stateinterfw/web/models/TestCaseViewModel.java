package robtest.stateinterfw.web.models;

import org.hibernate.transform.ResultTransformer;

import java.util.Arrays;
import java.util.List;

public class TestCaseViewModel extends AbstractViewModel implements ResultTransformer {
    private int id;
    private String uid;
    private long inputCount;
    private long stateCount;

    public TestCaseViewModel(String... fields) {
        super(fields);
    }

    private TestCaseViewModel(int id, long inputCount, long stateCount, String uid) {
        super(null);
        this.id = id;
        this.inputCount = inputCount;
        this.stateCount = stateCount;
        this.uid = uid;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        return new TestCaseViewModel(
                this.<Integer>get("id", tuple),
                this.<Long>get("inputCount", tuple),
                this.<Long>get("stateCount", tuple),
                this.<String>get("uid", tuple)
        );
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }

    public int getId() {
        return id;
    }

    public long getInputCount() {
        return inputCount;
    }

    public long getStateCount() {
        return stateCount;
    }

    public String getUid() {
        return uid;
    }
}
