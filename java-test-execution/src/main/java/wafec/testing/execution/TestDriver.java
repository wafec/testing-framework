package wafec.testing.execution;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDriver extends AbstractTestDriver {

    @Override
    protected List<TestDriverObservedOutput> runTestInput(TestInput testInput) {
        return null;
    }
}
