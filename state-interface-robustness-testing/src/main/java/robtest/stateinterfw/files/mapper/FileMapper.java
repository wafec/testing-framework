package robtest.stateinterfw.files.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import robtest.stateinterfw.data.IData;
import robtest.stateinterfw.files.IFileObject;

public class FileMapper implements IFileMapper {
    private Mapper _mapper;

    public FileMapper() {
        _mapper = DozerBeanMapperBuilder.buildDefault();
    }

    @Override
    public <T extends IData> T map(IFileObject fileObject, Class<T> dataClass) {
        return _mapper.map(fileObject, dataClass);
    }
}
