package robtest.stateinterfw;

import java.util.List;

public interface IOrderedCollection<T extends IOrderedElement> {
    int size();
    T get(int index);
}
