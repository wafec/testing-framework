package robtest.stateinterfw;

import java.util.*;

public abstract class MutatorCatalog implements IMutatorCatalog {
    private Map<String, IMutator> mutators;
    private static Random random = new Random();

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

    @Override
    public List<String> getAllKeys() {
        return new ArrayList<>(mutators.keySet());
    }
}
