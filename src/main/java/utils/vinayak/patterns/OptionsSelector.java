package utils.vinayak.patterns;

import java.util.LinkedHashMap;
import java.util.List;

public class OptionsSelector<Option, Key, Value> extends LinkedHashMap<Option, Condition<Key, Value>> {
    public List<Option> getSelectableOptions(Getter<Key, Value> data) {
        return this.entrySet().stream()
                .filter(entry -> entry.getValue().satisfies(data))
                .map(entry -> entry.getKey())
                .toList();
    }

    public Option selectFirstViableOption(Getter<Key, Value> data) {
        return this.entrySet().stream()
                .filter(entry -> entry.getValue().satisfies(data))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse(null);
    }
}
