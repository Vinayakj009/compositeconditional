package utils.vinayak.patterns.CompositeConditional.Interfaces;

import java.util.Map;

public interface CompositeConfig<K, V> {
    public void parseConfig(ConfigParser<K, V> parser, Map<String, Object> data);
}
