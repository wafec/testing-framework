package robtest.stateinterfw;

public interface IMessageArgumentFault extends IMessageArgument {
    String getOriginalDataValue();
    String getFaultData();
    IFault getFault();
}
