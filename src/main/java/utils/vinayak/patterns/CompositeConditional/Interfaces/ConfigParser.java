package utils.vinayak.patterns.CompositeConditional.Interfaces;

public interface ConfigParser<K, V> {
    Condition<K, V> parseCondition(Object condition);

    Condition<K, V> parseCondition(String condition);

    void addClass(String className, Class<?> clazz);

    Operation<V> parseOperation(Object operation);
}
