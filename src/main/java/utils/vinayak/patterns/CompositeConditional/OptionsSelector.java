package utils.vinayak.patterns.CompositeConditional;

import java.util.LinkedHashMap;
import java.util.List;

import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Getter;

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
