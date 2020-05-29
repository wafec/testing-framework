package robtest.stateinterfw.rabbit.management;

public interface IExchangeModel {
    String getName();
    String getExchangeType();
    boolean getDurable();
    boolean getAutoDelete();
    boolean getInternal();
}
