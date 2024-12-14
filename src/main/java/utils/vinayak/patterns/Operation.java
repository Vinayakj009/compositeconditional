package utils.vinayak.patterns;

@FunctionalInterface
public interface Operation<V> {
    boolean satisfies(V a, V b);
}
