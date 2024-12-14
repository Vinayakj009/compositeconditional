package utils.vinayak.patterns;

@FunctionalInterface
public interface Condition<K,V> {
    boolean satisfies(Getter<K,V> data);
}