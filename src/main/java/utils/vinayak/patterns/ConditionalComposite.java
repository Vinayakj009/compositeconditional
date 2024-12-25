package utils.vinayak.patterns;

import java.util.LinkedHashMap;
import java.util.List;

public class ConditionalComposite<Option, Key, Value> extends LinkedHashMap<Option, Condition<Key, Value>> {
    public List<Option> getSatisfiableOptions(Getter<Key, Value> data) {
        return this.entrySet().stream()
                .filter(entry -> entry.getValue().satisfies(data))
                .map(entry -> entry.getKey())
                .toList();
    }

    public Option getFirstSatisfiableOption(Getter<Key, Value> data) {
        return this.entrySet().stream()
                .filter(entry -> entry.getValue().satisfies(data))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse(null);
    }
}
