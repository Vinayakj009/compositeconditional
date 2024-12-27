package utils.vinayak.patterns.CompositeConditional;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SatisfySomeTest {
    @Test
    public void testSatisfiesSomeTrue() {
        SatisfySome<Object, Object> satisfySome = SatisfySome.builder()
                .satisfyMinimum(2)
                .conditions(List.of(key -> true, key -> true, key -> false))
                .build();
        assert satisfySome.satisfies(key -> null);
    }

    @Test
    public void testSatisfiesSomeFalse() {
        SatisfySome<Object, Object> satisfySome = SatisfySome.builder()
                .satisfyMinimum(3)
                .conditions(List.of(key -> true, key -> false, key -> true, key -> false))
                .build();
        assert !satisfySome.satisfies(key -> null);
    }
}
