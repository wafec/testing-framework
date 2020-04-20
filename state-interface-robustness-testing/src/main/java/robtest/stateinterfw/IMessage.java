package robtest.stateinterfw;

import java.util.Date;

public interface IMessage {
    int getId();
    String getSource();
    String getDestination();
    String getName();
    IMessageArgs getArgs();
    long getSequenceId();
    Date getEntryDate();
}
