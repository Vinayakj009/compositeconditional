package utils.vinayak.patterns;

import org.junit.jupiter.api.Test;

public class BaseConditionTest {

    @Test
    public void testSatisfiesTrue() {
        BaseCondition<Object, Object> baseCondition = BaseCondition.builder()
                .with((input, compareTo) -> true)
                .build();
        assert baseCondition.satisfies(key -> null);
    }

    @Test
    public void testSatisfiesFalse() {
        // Arrange
        BaseCondition<Object, Object> baseCondition = BaseCondition.builder()
                .with((input, compareTo) -> false)
                .build();
        assert !baseCondition.satisfies(key -> null);
    }

    @Test
    public void testCompareInput() {
        Object key = new Object();
        Object input = new Object();
        Object to = new Object();
        BaseCondition<Object, Object> baseCondition = BaseCondition.builder()
                .with((suppliedInput, suppliedTo) -> {
                    return suppliedInput == input && suppliedTo == to;
                })
                .compare(key)
                .to(to)
                .build();
        assert baseCondition.satisfies(suppliedKey -> {
            if (suppliedKey == key) {
                return input;
            }
            return null;
        });
    }
}
