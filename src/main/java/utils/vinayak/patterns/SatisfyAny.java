package utils.vinayak.patterns;

import java.util.List;

import lombok.Builder;

@Builder
public class SatisfyAny<K,V> implements Condition<K,V> {
    private final List<Condition<K,V>> conditions;

    @Override
    public boolean satisfies(Getter<K,V> data) {
        return !conditions
                .stream()
                .filter(condition -> condition.satisfies(data))
                .findFirst()
                .isEmpty();
    }
}
