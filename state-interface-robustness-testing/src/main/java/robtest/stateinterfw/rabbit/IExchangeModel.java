package robtest.stateinterfw.rabbit;

public interface IExchangeModel {
    String getName();
    String getExchangeType();
    boolean getDurable();
    boolean getAutoDelete();
    boolean getInternal();
}
