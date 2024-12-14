package utils.vinayak.patterns;

@FunctionalInterface
public interface Getter<K,V> {
    public V get(K key);
}
