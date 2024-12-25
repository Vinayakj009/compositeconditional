package utils.vinayak.patterns.CompositeConditional;

import java.util.List;

import lombok.Builder;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Getter;

@Builder
public class SatisfyAll<K, V> implements Condition<K, V> {
    private final List<Condition<K, V>> conditions;

    @Override
    public boolean satisfies(Getter<K, V> data) {
        return conditions
                .stream()
                .filter(condition -> !condition.satisfies(data))
                .findFirst()
                .isEmpty();
    }
}
