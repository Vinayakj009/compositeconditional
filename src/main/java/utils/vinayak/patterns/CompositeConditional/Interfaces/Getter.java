package utils.vinayak.patterns.CompositeConditional.Interfaces;

@FunctionalInterface
public interface Getter<K, V> {
    public V get(K key);
}
