package utils.vinayak.patterns;

import lombok.Builder;

@Builder
public class BaseCondition<K, V> implements Condition<K, V> {
    private final K compare;
    private final Operation<V> with;
    private final V to;

    @Override
    public boolean satisfies(Getter<K,V> data) {
        return with.satisfies(data.get(compare), to);
    }
}
