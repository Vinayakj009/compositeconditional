package utils.vinayak.patterns;

import java.util.List;

import org.junit.Test;

public class SatisfyAllTest {

    @Test
    public void testSatisfyAllTrue() {
        SatisfyAll<Object, Object> satisfyAll = SatisfyAll.builder()
                .conditions(List.of(key -> true, key -> true))
                .build();
        assert satisfyAll.satisfies(key -> null);
    }

    @Test
    public void testSatisfyAllFalse() {
        SatisfyAll<Object, Object> satisfyAll = SatisfyAll.builder()
                .conditions(List.of(key -> true, key -> false))
                .build();
        assert !satisfyAll.satisfies(key -> null);
    }
}
