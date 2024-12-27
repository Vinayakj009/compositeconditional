package utils.vinayak.patterns.CompositeConditional;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.vinayak.patterns.CompositeConditional.Interfaces.CompositeConfig;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.ConfigParser;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Getter;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Operation;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseCondition<K, V> implements Condition<K, V>, CompositeConfig<K, V> {
    @Setter
    private K compare;
    @Setter
    private V to;
    private Operation<V> with;

    @Override
    public boolean satisfies(Getter<K, V> data) {
        return with.satisfies(data.get(compare), to);
    }

    @Override
    public void parseConfig(ConfigParser<K, V> parser, Map<String, Object> data) {
        Map<String, Object> operation = (Map<String, Object>) data.get("operation");
        this.with = parser.parseOperation(operation);
    }
}
