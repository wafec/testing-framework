package wafec.testing.execution;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TEST_INPUT")
public class TestInput {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int position;
    @ManyToOne
    @JoinColumn(name = "test_case_id", referencedColumnName = "id")
    private TestCase testCase;
    private String name;
}
