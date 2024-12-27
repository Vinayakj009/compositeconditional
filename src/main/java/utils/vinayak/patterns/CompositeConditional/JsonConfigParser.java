package utils.vinayak.patterns.CompositeConditional;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.vinayak.patterns.CompositeConditional.Interfaces.CompositeConfig;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.ConfigParser;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Operation;

public class JsonConfigParser<K, V> implements ConfigParser<K, V> {
    private Map<String, Class<?>> classMap = new HashMap<>();
    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Condition<K, V> parseCondition(Object condition) {
        RawConfig rawConfig = objectMapper.convertValue(condition, RawConfig.class);
        Class<?> clazz = classMap.get(rawConfig.getClassName());
        if (clazz == null) {
            throw new RuntimeException("Class not found");
        }
        if (!CompositeConfig.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Class does not implement CompositeConfig interface");
        }
        Condition<K, V> output = (Condition<K, V>) objectMapper.convertValue(rawConfig.getData(), clazz);
        if (CompositeConfig.class.isAssignableFrom(output.getClass())) {
            CompositeConfig<K, V> compositeConfig = (CompositeConfig<K, V>) output;
            compositeConfig.parseConfig(this, rawConfig.getData());
        }
        return output;
    }

    public Condition<K, V> parseCondition(String condition) {
        RawConfig rawConfig;
        try {
            rawConfig = objectMapper.readValue(condition, RawConfig.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing json");
        }
        Class<?> clazz = classMap.get(rawConfig.getClassName());
        if (clazz == null) {
            throw new RuntimeException("Class not found");
        }
        if (!Condition.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Class does not implement CompositeConfig interface");
        }
        Condition<K, V> output = (Condition<K, V>) objectMapper.convertValue(rawConfig.getData(), clazz);
        if (CompositeConfig.class.isAssignableFrom(output.getClass())) {
            CompositeConfig<K, V> compositeConfig = (CompositeConfig<K, V>) output;
            compositeConfig.parseConfig(this, rawConfig.getData());
        }
        return output;
    }

    public void addClass(String className, Class<?> clazz) {
        classMap.put(className, clazz);
    }

    public Operation<V> parseOperation(Object operation) {
        RawConfig rawConfig = objectMapper.convertValue(operation, RawConfig.class);
        Class<?> clazz = classMap.get(rawConfig.getClassName());
        if (!Operation.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Class does not implement CompositeConfig interface");
        }
        Operation<V> output = (Operation<V>) objectMapper.convertValue(rawConfig.getData(), clazz);
        if (CompositeConfig.class.isAssignableFrom(output.getClass())) {
            CompositeConfig<K, V> compositeConfig = (CompositeConfig<K, V>) output;
            compositeConfig.parseConfig(this, rawConfig.getData());
        }
        return output;
    }
}
