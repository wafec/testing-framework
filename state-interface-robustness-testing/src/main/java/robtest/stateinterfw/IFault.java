package robtest.stateinterfw;

public interface IFault {
    int getId();
    String getName();
    String getTargetDataType();
    String getCategory();
    String apply(String value);
    String getFaultKey();
}
