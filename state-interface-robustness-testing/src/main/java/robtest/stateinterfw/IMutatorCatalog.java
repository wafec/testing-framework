package robtest.stateinterfw;

import java.util.List;

public interface IMutatorCatalog {
    IMutator findOne(String key);
    void add(IMutator mutator);
    List<String> getAllKeys();
}
