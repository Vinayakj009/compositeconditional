package utils.vinayak.patterns;

import java.util.List;

import lombok.Builder;

@Builder
public class SatisfySome<K, V> implements Condition<K, V> {
    private final List<Condition<K, V>> conditions;
    private final int satisfyMinimum;

    @Override
    public boolean satisfies(Getter<K, V> data) {
        return conditions
                .stream()
                .filter(condition -> condition.satisfies(data))
                .limit(satisfyMinimum)
                .count() == satisfyMinimum;
    }
}
