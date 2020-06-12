package robtest.stateinterfw.web.models;

public class TestPlanCreateRequestModel {
    private String name;
    private String operation;
    private Integer planId;

    public TestPlanCreateRequestModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
