package robtest.stateinterfw.hbm;

import robtest.stateinterfw.IFault;
import robtest.stateinterfw.IMutantMessageArgument;

public class MutantMessageArgument implements IMutantMessageArgument {
    private int id;
    private Fault fault;
    private MessageArgument originalMessageArgument;
    private String mutationDataValue;
    private MutantTestCase mutantTestCase;

    public MutantMessageArgument() {

    }

    public MutantMessageArgument(Fault fault, MessageArgument originalMessageArgument, String mutationDataValue, MutantTestCase mutantTestCase) {
        this.fault = fault;
        this.originalMessageArgument = originalMessageArgument;
        this.mutationDataValue = mutationDataValue;
        this.mutantTestCase = mutantTestCase;
    }

    @Override
    public String getOriginalDataValue() {
        evalMessageArg();
        return originalMessageArgument.getDataValue();
    }

    @Override
    public String getMutationDataValue() {
        return mutationDataValue;
    }

    public void setMutationDataValue(String mutationDataValue) {
        this.mutationDataValue = mutationDataValue;
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
        return this.mutationDataValue;
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

    public void setMutantTestCase(MutantTestCase mutantTestCase) {
        this.mutantTestCase = mutantTestCase;
    }

    public MutantTestCase getMutantTestCase() {
        return this.mutantTestCase;
    }
}
