package robtest.stateinterfw.data.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DataMapper implements IDataMapper {
    private Mapper _mapper;

    public DataMapper() {
        _mapper = DozerBeanMapperBuilder.buildDefault();
    }

    @Override
    public void map(Object source, Object destination) {
        _mapper.map(source, destination);
    }

    @Override
    public <T> T map(Object source, Class<T> destinationClass) {
        return _mapper.map(source, destinationClass);
    }
}
