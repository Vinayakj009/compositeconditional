package utils.vinayak.patterns.CompositeConditional.Interfaces;

import java.util.Map;
import java.util.function.Consumer;

public interface ConfigParser<K, V> {
    void parseConditions(Map<String, Object> data, Consumer<Condition<K, V>> consumer);

    Condition<K, V> parseCondition(String condition);

    void addClass(String className, Class<?> clazz);

    Operation<V> parseOperation(Map<String, Object> operation);
}
