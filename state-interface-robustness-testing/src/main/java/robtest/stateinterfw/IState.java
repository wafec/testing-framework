package robtest.stateinterfw;

public interface IState {
    int getId();
    String getName();
    int getTimeout();
    boolean isRequired();
}
