package robtest.stateinterfw;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MutatorCatalog implements IMutatorCatalog {
    private Map<String, IMutator> mutatorsKeyMap;
    private Map<String, List<IMutator>> mutatorsCategoryMap;
    private static Random random = new Random();

    public MutatorCatalog() {
        this.mutatorsKeyMap = new TreeMap<>();
        this.mutatorsCategoryMap = new TreeMap<>();
    }

    @Override
    public IMutator findOne(String key) {
        IMutator mutator = null;
        if (mutatorsKeyMap.containsKey(key)) {
            mutator = mutatorsKeyMap.get(key);
        }
        return mutator;
    }

    @Override
    public void add(IMutator mutator) {
        mutatorsKeyMap.put(mutator.getKey(), mutator);
        List<IMutator> mutators = new ArrayList<>();
        if (mutatorsCategoryMap.containsKey(mutator.getCategory()))
            mutators = mutatorsCategoryMap.get(mutator.getCategory());
        mutators.removeIf(m -> m.getKey().equals(mutator.getKey()));
        mutators.add(mutator);
        mutatorsCategoryMap.put(mutator.getCategory(), mutators);
    }

    @Override
    public List<String> getAllKeys() {
        return new ArrayList<>(mutatorsKeyMap.keySet());
    }

    @Override
    public List<IMutator> find(String category) {
        List<IMutator> mutators = null;
        if (mutatorsCategoryMap.containsKey(category)) {
            mutators = mutatorsCategoryMap.get(category);
        }
        return mutators;
    }

    @Override
    public List<IMutator> filterBy(IFaultMutatorFilter filter, String dataType) {
        return mutatorsKeyMap.values().stream()
                .filter(m -> filter.canApply(m, dataType))
                .collect(Collectors.toList());
    }
}
