package robtest.stateinterfw.rabbit.commandline;

import com.rabbitmq.client.Channel;

import java.io.IOException;

public interface IRabbitCommandLineConsumer {
    void accept(Channel channel) throws IOException;
}
