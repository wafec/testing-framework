package robtest.stateinterfw.rabbit;

public class QueueBind implements IQueueBind {
    private int id;
    private Queue queue;
    private Queue testQueue;

    public QueueBind() {

    }

    @Override
    public IQueue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    @Override
    public IQueue getTestQueue() {
        return testQueue;
    }

    public void setTestQueue(Queue testQueue) {
        this.testQueue = testQueue;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
