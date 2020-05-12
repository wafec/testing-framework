package robtest.stateinterfw.files.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import robtest.stateinterfw.data.IData;
import robtest.stateinterfw.files.IFileObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileMapper implements IFileMapper {
    public FileMapper() {

    }

    @Override
    public <T extends IData> T map(IFileObject fileObject, Class<T> dataClass) {
        throw new UnsupportedOperationException();
    }
}
