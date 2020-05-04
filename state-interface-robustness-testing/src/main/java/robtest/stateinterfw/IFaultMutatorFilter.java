package robtest.stateinterfw;

public interface IFaultMutatorFilter {
    boolean canApply(IMutator mutator, String targetDataType);
}
