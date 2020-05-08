package robtest.stateinterfw.files.mapper;

import robtest.stateinterfw.data.IData;
import robtest.stateinterfw.files.IFileObject;

public interface IFileMapper {
    <T extends IData> T map(IFileObject fileObject, Class<T> dataClass);
}
