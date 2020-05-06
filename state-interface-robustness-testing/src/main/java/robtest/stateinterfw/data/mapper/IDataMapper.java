package robtest.stateinterfw.data.mapper;

public interface IDataMapper {
    void map(Object source, Object destination);
    <T> T map(Object source, Class<T> destinationClass);
}
