package robtest.stateinterfw;

import java.util.List;

public interface IMutatorCatalog {
    IMutator findOne(String key);
    List<IMutator> find(String category);
    void add(IMutator mutator);
    List<String> getAllKeys();
    List<IMutator> filterBy(IFaultMutatorFilter filter, String dataType);
}
