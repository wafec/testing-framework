package robtest.stateinterfw;

public interface IMutator {
    String getKey();
    String mutate(String value);
}
