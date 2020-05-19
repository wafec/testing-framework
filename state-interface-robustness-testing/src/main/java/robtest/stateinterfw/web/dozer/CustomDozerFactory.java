package robtest.stateinterfw.web.dozer;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import robtest.stateinterfw.web.MessageDeviceRabbitController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomDozerFactory {
    private CustomDozerFactory() { }

    public static Mapper buildWeb() {
        return DozerBeanMapperBuilder.create()
                .withXmlMapping(() -> {
                    InputStream is = MessageDeviceRabbitController.class.getResourceAsStream("/mapper/web-mappings.xml");
                    try {
                        return new ByteArrayInputStream(is.readAllBytes());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                        throw new IllegalArgumentException();
                    }
                })
                .build();
    }
}
