package robtest.core;

public interface IFunction<K1, K2, T> {
    T apply(K1 k1, K2 k2);
}
