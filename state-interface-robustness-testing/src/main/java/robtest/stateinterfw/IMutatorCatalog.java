package robtest.stateinterfw;

public interface IMutatorCatalog {
    IMutator findOne(String key);
    void add(IMutator mutator);
}
