package utils.vinayak.patterns.CompositeConditional.Interfaces;

@FunctionalInterface
public interface Condition<K, V> {
    boolean satisfies(Getter<K, V> data);
}