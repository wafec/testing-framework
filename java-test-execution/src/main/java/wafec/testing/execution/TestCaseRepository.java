package wafec.testing.execution;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {
    List<TestCase> findByTargetSystem(String targetSystem);
}
