package robtest.stateinterfw;

public interface IOrderedCollection<T extends IOrderedElement> {
    int size();
    T get(int index);
}
