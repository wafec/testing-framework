package robtest.stateinterfw.hbm;

import robtest.stateinterfw.IMessage;
import robtest.stateinterfw.IMessageArgs;

import java.util.Date;
import java.util.Set;

public class Message implements IMessage {
    private int id;
    private String source;
    private String destination;
    private String name;
    private long sequenceId;
    private Date entryDate;
    private Set<MessageArgument> messageArguments;

    public Message() {

    }

    public Message(String source, String destination, String name, long sequenceId, Date entryDate) {
        this.source = source;
        this.destination = destination;
        this.name = name;
        this.sequenceId = sequenceId;
        this.entryDate = entryDate;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IMessageArgs getArgs() {
        return new MessageArgs(messageArguments);
    }

    @Override
    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Set<MessageArgument> getMessageArguments() {
        return this.messageArguments;
    }

    public void setMessageArguments(Set<MessageArgument> messageArguments) {
        this.messageArguments = messageArguments;
    }
}
