package robtest.stateinterfw.rabbit;

public class RabbitTestBind implements IRabbitTestBind {
    private int id;
    private RabbitBind oldBind;
    private RabbitBind sourceBind;
    private RabbitBind destinationBind;
    private RabbitMessageDevice messageDevice;

    public RabbitTestBind() {

    }

    public RabbitTestBind(RabbitBind oldBind, RabbitBind sourceBind, RabbitBind destinationBind,
                          RabbitMessageDevice messageDevice) {
        this.oldBind = oldBind;
        this.sourceBind = sourceBind;
        this.destinationBind = destinationBind;
        this.messageDevice = messageDevice;
    }

    @Override
    public IRabbitBind getOldBind() {
        return oldBind;
    }

    @Override
    public IRabbitBind getSourceBind() {
        return sourceBind;
    }

    public void setSourceBind(RabbitBind sourceBind) {
        this.sourceBind = sourceBind;
    }

    @Override
    public IRabbitBind getDestinationBind() {
        return destinationBind;
    }

    @Override
    public IRabbitMessageDevice getMessageDevice() {
        return messageDevice;
    }

    public void setMessageDevice(RabbitMessageDevice messageDevice) {
        this.messageDevice = messageDevice;
    }

    public void setDestinationBind(RabbitBind destinationBind) {
        this.destinationBind = destinationBind;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setOldBind(RabbitBind oldBind) {
        this.oldBind = oldBind;
    }
}
