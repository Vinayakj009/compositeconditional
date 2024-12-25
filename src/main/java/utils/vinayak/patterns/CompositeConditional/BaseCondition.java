package utils.vinayak.patterns.CompositeConditional;

import lombok.Builder;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Getter;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Operation;

@Builder
public class BaseCondition<K, V> implements Condition<K, V> {
    private final K compare;
    private final Operation<V> with;
    private final V to;

    @Override
    public boolean satisfies(Getter<K, V> data) {
        return with.satisfies(data.get(compare), to);
    }
}
