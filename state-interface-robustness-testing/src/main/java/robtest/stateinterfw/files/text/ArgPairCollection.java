package robtest.stateinterfw.files.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArgPairCollection {
    private final List<ArgPair> list = new ArrayList<>();

    public ArgPair get(String name) {
        for (var pair : list) {
            if (pair.getName().equals(name))
                return pair;
        }
        return null;
    }

    public Optional<String> getValue(String name) {
        return Optional.ofNullable(get(name)).map(ArgPair::getValue);
    }

    public void add(ArgPair pair) {
        this.list.add(pair);
    }

    public List<ArgPair> getList() {
        return list;
    }

    public static ArgPairCollection parse(String[] args) {
        ArgPairCollection collection = new ArgPairCollection();
        for (var arg : args) {
            try {
                var pair = ArgPair.parse(arg);
                collection.add(pair);
            } catch (IllegalArgumentException exc) {
                // SKIP
            }
        }
        return collection;
    }

    public void remove(String name) {
        for (int i = 0 ; i < list.size(); ) {
            ArgPair pair = list.get(i);
            if (pair.getName().equals(name))
                list.remove(i);
            else
                i++;
        }
    }
}
