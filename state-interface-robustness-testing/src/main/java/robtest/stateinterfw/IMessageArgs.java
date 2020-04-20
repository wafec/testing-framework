package robtest.stateinterfw;

import java.util.List;

public interface IMessageArgs extends IArgs<IMessageArgument> {
    List<String> getDistinctDataTypes();
    List<IMessageArgument> getByDataType(String typeName);
}
