package robtest.stateinterfw;

public interface IMutantMessageArgument extends IMessageArgument {
    String getOriginalDataValue();
    String getMutationDataValue();
    IFault getFault();
}
