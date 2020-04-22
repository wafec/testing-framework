package robtest.stateinterfw.data;

import robtest.stateinterfw.IMessageArgs;
import robtest.stateinterfw.IMessageArgument;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MessageArgs implements IMessageArgs {
    private Set<MessageArgument> messageArguments;

    public MessageArgs(Set<MessageArgument> messageArguments) {
        this.messageArguments = messageArguments;
    }

    @Override
    public List<String> getDistinctDataTypes() {
        List<String> distinct = new ArrayList<>();
        for (Iterator<MessageArgument> it = messageArguments.iterator(); it.hasNext();) {
            MessageArgument messageArgument = it.next();
            if (!distinct.contains(messageArgument.getDataType())) {
                distinct.add(messageArgument.getDataType());
            }
        }
        return distinct;
    }

    @Override
    public List<IMessageArgument> getByDataType(String typeName) {
        List<IMessageArgument> filtered = new ArrayList<>();
        for (Iterator<MessageArgument> it = messageArguments.iterator(); it.hasNext();) {
            MessageArgument messageArgument = it.next();
            if (messageArgument.getDataType().equals(typeName)) {
                filtered.add(messageArgument);
            }
        }
        return filtered;
    }

    @Override
    public int size() {
        return messageArguments.size();
    }

    @Override
    public IMessageArgument get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        IMessageArgument messageArgument = null;
        int i = 0;
        for (Iterator<MessageArgument> it = messageArguments.iterator(); it.hasNext() && i <= index; i++) {
            messageArgument = it.next();
        }
        return messageArgument;
    }
}
