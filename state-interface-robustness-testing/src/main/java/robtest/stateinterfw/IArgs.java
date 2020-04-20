package robtest.stateinterfw;

public interface IArgs<T extends IArgument> {
    int size();
    T get(int index);
}
