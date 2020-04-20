package robtest.stateinterfw;

public class FrameContext {
    private FrameContext() { }

    public static class Mutator {
        private static IMutatorCatalog _mutatorCatalog = new MutatorCatalog();

        public static void define(IMutatorCatalog mutatorCatalog) {
            _mutatorCatalog = mutatorCatalog;
        }

        public static IMutatorCatalog current() {
            return _mutatorCatalog;
        }
    }
}
