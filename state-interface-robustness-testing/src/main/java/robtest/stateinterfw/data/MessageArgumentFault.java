package robtest.stateinterfw.data;

import robtest.stateinterfw.IFault;
import robtest.stateinterfw.IMessageArgumentFault;

public class MessageArgumentFault implements IMessageArgumentFault {
    private int id;
    private Fault fault;
    private MessageArgument originalMessageArgument;
    private String faultData;
    private TestCaseFault testCaseFault;

    public MessageArgumentFault() {

    }

    public MessageArgumentFault(Fault fault, MessageArgument originalMessageArgument, String faultData, TestCaseFault testCaseFault) {
        this.fault = fault;
        this.originalMessageArgument = originalMessageArgument;
        this.faultData = faultData;
        this.testCaseFault = testCaseFault;
    }

    @Override
    public String getOriginalDataValue() {
        evalMessageArg();
        return originalMessageArgument.getDataValue();
    }

    @Override
    public String getFaultData() {
        return faultData;
    }

    public void setFaultData(String faultData) {
        this.faultData = faultData;
    }

    @Override
    public IFault getFault() {
        return fault;
    }

    public void setFault(Fault fault) {
        this.fault = fault;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getOrder() {
        evalMessageArg();
        return this.originalMessageArgument.getOrder();
    }

    @Override
    public String getName() {
        evalMessageArg();
        return this.originalMessageArgument.getName();
    }

    @Override
    public String getDataType() {
        evalMessageArg();
        return this.originalMessageArgument.getDataType();
    }

    @Override
    public String getDataValue() {
        return this.faultData;
    }

    void evalMessageArg() {
        if (this.originalMessageArgument == null)
            throw new NullPointerException();
    }

    public void setOriginalMessageArgument(MessageArgument messageArgument) {
        this.originalMessageArgument = originalMessageArgument;
    }

    public MessageArgument getOriginalMessageArgument() {
        return this.originalMessageArgument;
    }

    public void setTestCaseFault(TestCaseFault testCaseFault) {
        this.testCaseFault = testCaseFault;
    }

    public TestCaseFault getTestCaseFault() {
        return this.testCaseFault;
    }
}
