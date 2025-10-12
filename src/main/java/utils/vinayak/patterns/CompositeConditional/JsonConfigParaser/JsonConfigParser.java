package utils.vinayak.patterns.CompositeConditional.JsonConfigParaser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.vinayak.patterns.CompositeConditional.Interfaces.Configurable;
import utils.vinayak.patterns.CompositeConditional.Constants;
import utils.vinayak.patterns.CompositeConditional.RawConfig;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.ConfigParser;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Operation;

public class JsonConfigParser<K, V> implements ConfigParser<K, V> {
    private Map<String, Class<?>> classMap = new HashMap<>();
    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void parseConditions(Map<String, Object> data, java.util.function.Consumer<Condition<K, V>> consumer) {
        Collection<Map<String, Object>> conditions = (Collection<Map<String, Object>>) data.get(Constants.CONDITION);
        for (Map<String, Object> condition : conditions) {
            consumer.accept(parseCondition(condition));
        }
    }

    private Condition<K, V> parseCondition(Object condition) {
        RawConfig rawConfig = objectMapper.convertValue(condition, RawConfig.class);
        Class<?> clazz = classMap.get(rawConfig.getClassName());
        if (clazz == null) {
            throw new RuntimeException(Constants.EXCEPTION_CLASS_NOT_FOUND);
        }
        if (!Configurable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException(Constants.EXCEPTION_CLASS_DOES_NOT_IMPLEMENT);
        }
        Condition<K, V> output = (Condition<K, V>) objectMapper.convertValue(rawConfig.getData(), clazz);
        if (Configurable.class.isAssignableFrom(output.getClass())) {
            Configurable<K, V> compositeConfig = (Configurable<K, V>) output;
            compositeConfig.parseConfig(this, rawConfig.getData());
        }
        return output;
    }

    public Condition<K, V> parseCondition(String condition) {
        RawConfig rawConfig;
        try {
            rawConfig = objectMapper.readValue(condition, RawConfig.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(Constants.EXCEPTION_ERROR_PARSING_JSON);
        }
        return this.parseCondition(rawConfig);
    }

    public void addClass(String className, Class<?> clazz) {
        classMap.put(className, clazz);
    }

    public Operation<V> parseOperation(Map<String, Object> data) {
        Map<String, Object> operation = (Map<String, Object>) data.get(Constants.OPERATION);
        RawConfig rawConfig = objectMapper.convertValue(operation, RawConfig.class);
        Class<?> clazz = classMap.get(rawConfig.getClassName());
        if (!Operation.class.isAssignableFrom(clazz)) {
            throw new RuntimeException(Constants.EXCEPTION_CLASS_DOES_NOT_IMPLEMENT);
        }
        Operation<V> output = (Operation<V>) objectMapper.convertValue(rawConfig.getData(), clazz);
        if (Configurable.class.isAssignableFrom(output.getClass())) {
            Configurable<K, V> compositeConfig = (Configurable<K, V>) output;
            compositeConfig.parseConfig(this, rawConfig.getData());
        }
        return output;
    }
}
