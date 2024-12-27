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
@NoArgsConstructor
@AllArgsConstructor
public class SatisfySome<K, V> implements Condition<K, V>, CompositeConfig<K, V> {
    private List<Condition<K, V>> conditions;
    private int satisfyMinimum;

    @Override
    public boolean satisfies(Getter<K, V> data) {
        return conditions
                .stream()
                .filter(condition -> condition.satisfies(data))
                .limit(satisfyMinimum)
                .count() == satisfyMinimum;
    }

    @Override
    public void parseConfig(ConfigParser<K, V> parser, Map<String, Object> data) {
        List<Map<String, Object>> conditions = (List<Map<String, Object>>) data.get("conditions");
        this.conditions = new ArrayList<>();
        this.satisfyMinimum = (int) data.get("satisfyMinimum");
        for (Map<String, Object> condition : conditions) {
            this.conditions.add(parser.parseCondition(condition));
        }
    }
}
