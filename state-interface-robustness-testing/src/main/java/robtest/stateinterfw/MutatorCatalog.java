package robtest.stateinterfw;

import java.util.Map;
import java.util.TreeMap;

public class MutatorCatalog implements IMutatorCatalog {
    private Map<String, IMutator> mutators;

    public MutatorCatalog() {
        this.mutators = new TreeMap<>();
    }

    @Override
    public IMutator findOne(String key) {
        IMutator mutator = null;
        if (mutators.containsKey(key)) {
            mutator = mutators.get(key);
        }
        return mutator;
    }

    @Override
    public void add(IMutator mutator) {
        mutators.put(mutator.getKey(), mutator);
    }
}
