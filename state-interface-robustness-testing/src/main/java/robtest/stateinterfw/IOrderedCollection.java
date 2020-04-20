package robtest.stateinterfw;

public interface IOrderedCollection<T extends IOrderedElement> {
    int size();
    IOrderedElement get(int index);
}
