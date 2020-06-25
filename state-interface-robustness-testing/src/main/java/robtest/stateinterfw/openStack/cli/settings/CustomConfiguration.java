package robtest.stateinterfw.openStack.cli.settings;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import robtest.stateinterfw.openStack.cli.BaseClient;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;


@JacksonXmlRootElement(localName = "custom")
public class CustomConfiguration {
    private ClientConfiguration clientConfiguration;

    public ClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    @JacksonXmlProperty(localName = "openstack-client")
    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    public static Optional<CustomConfiguration> instance() {
        CustomConfiguration customConfiguration = null;
        try {
            var path = BaseClient.class.getResource("/openstack-client-settings.xml");
            XmlMapper mapper = new XmlMapper();
            String content = Files.readString(new File(path.toURI()).toPath());
            customConfiguration = mapper.readValue(content, CustomConfiguration.class);
        } catch (URISyntaxException | IOException exc) {
            exc.printStackTrace();
        }
        return Optional.of(customConfiguration);
    }
}
