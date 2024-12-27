package utils.vinayak.patterns.CompositeConditional;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SatisfyAnyTest {

    @Test
    public void testSatisfyAnyTrue() {
        SatisfyAny<Object, Object> satisfyAny = SatisfyAny.builder()
                .conditions(List.of(key -> false, key -> true))
                .build();
        assert satisfyAny.satisfies(key -> null);
    }

    @Test
    public void testSatisfyAnyFalse() {
        SatisfyAny<Object, Object> satisfyAny = SatisfyAny.builder()
                .conditions(List.of(key -> false, key -> false))
                .build();
        assert !satisfyAny.satisfies(key -> null);
    }
}
