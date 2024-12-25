package utils.vinayak.patterns.CompositeConditional.Interfaces;

@FunctionalInterface
public interface Operation<V> {
    boolean satisfies(V a, V b);
}
