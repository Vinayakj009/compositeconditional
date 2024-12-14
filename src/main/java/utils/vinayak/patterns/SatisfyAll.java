package utils.vinayak.patterns;

import java.util.List;

import lombok.Builder;

@Builder
public class SatisfyAll<K,V> implements Condition<K,V> {
    private final List<Condition<K,V>> conditions;

    @Override
    public boolean satisfies(Getter<K,V> data) {
        for (Condition<K,V> condition : conditions) {
            if (!condition.satisfies(data)) {
                return false;
            }
        }
        return true;
    }
}
