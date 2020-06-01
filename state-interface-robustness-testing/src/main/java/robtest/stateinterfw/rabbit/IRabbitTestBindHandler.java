package robtest.stateinterfw.rabbit;

public interface IRabbitTestBindHandler {
    void control(IRabbitTestBind rabbitTestBind);
    void leaveControl(IRabbitTestBind rabbitTestBind);
}
