package utils.vinayak.patterns.CompositeConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import utils.vinayak.patterns.CompositeConditional.Interfaces.CompositeConfig;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.ConfigParser;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Getter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SatisfyAny<K, V> implements Condition<K, V>, CompositeConfig<K, V> {
    private List<Condition<K, V>> conditions;

    @Override
    public boolean satisfies(Getter<K, V> data) {
        return !conditions
                .stream()
                .filter(condition -> condition.satisfies(data))
                .findFirst()
                .isEmpty();
    }

    @Override
    public void parseConfig(ConfigParser<K, V> parser, Map<String, Object> data) {
        this.conditions = new ArrayList<>();
        parser.parseConditions(data, this.conditions::add);
    }
}
