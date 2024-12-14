package utils.vinayak.patterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import lombok.Builder;

public class CompositeConditionalsTest {

    @Builder
    private static class TestCase {
        private final Getter<String, Integer> getter;
        private final Condition<String, Integer> condition;
        private final boolean expected;
    }

    @ParameterizedTest
    @MethodSource("getTestCaseNames")
    public void testCompositeConditionals(String testName) {
        TestCase testCase = getTestCases().get(testName);
        boolean actual = testCase.condition.satisfies(testCase.getter);
        assertEquals(testName, testCase.expected, actual);
    }

    private static List<String> getTestCaseNames() {
        return getTestCases().keySet().stream().sorted().toList();
    }

    private static Map<String, TestCase> getTestCases() {
        Map<String, TestCase> testCases = new HashMap<>();
        Operation<Integer> lessThan = (a, b) -> a < b;
        Operation<Integer> equal = (a, b) -> a.equals(b);
        Operation<Integer> greaterThan = (a, b) -> a > b;

        testCases.put("1. Single Condition less than expect true", TestCase.builder()
                .getter((key) -> 10)
                .condition(BaseCondition.<String, Integer>builder()
                        .with(lessThan)
                        .to(20)
                        .build())
                .expected(true)
                .build());
        testCases.put("2. Single Condition greaer than expect false", TestCase.builder()
                .getter((key) -> 10)
                .condition(BaseCondition.<String, Integer>builder()
                        .with(greaterThan)
                        .to(20)
                        .build())
                .expected(false)
                .build());
        testCases.put("3. SatisfyAll expect true", TestCase.builder()
                .getter((key) -> {
                    if (key == null) {
                        return 0;
                    }
                    return switch (key) {
                        case "a" -> 10;
                        case "b" -> 20;
                        case "c" -> 30;
                        default -> 0;
                    };
                })
                .condition(SatisfyAll.<String, Integer>builder()
                        .conditions(List.of(
                                BaseCondition.<String, Integer>builder()
                                        .compare("a")
                                        .with(lessThan)
                                        .to(20)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("b")
                                        .with(greaterThan)
                                        .to(5)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("c")
                                        .with(equal)
                                        .to(30)
                                        .build()))
                        .build())
                .expected(true)
                .build());
        testCases.put("4. SatisfyAll expect false", TestCase.builder()
                .getter((key) -> {
                    if (key == null) {
                        return 0;
                    }
                    return switch (key) {
                        case "a" -> 10;
                        case "b" -> 20;
                        case "c" -> 30;
                        default -> 0;
                    };
                })
                .condition(SatisfyAll.<String, Integer>builder()
                        .conditions(List.of(
                                BaseCondition.<String, Integer>builder()
                                        .compare("a")
                                        .with(lessThan)
                                        .to(5)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("b")
                                        .with(greaterThan)
                                        .to(5)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("c")
                                        .with(equal)
                                        .to(30)
                                        .build()))
                        .build())
                .expected(false)
                .build());
        testCases.put("5. SatisfyAny expect true", TestCase.builder()
                .getter((key) -> {
                    if (key == null) {
                        return 0;
                    }
                    return switch (key) {
                        case "a" -> 10;
                        case "b" -> 20;
                        case "c" -> 30;
                        default -> 0;
                    };
                })
                .condition(SatisfyAny.<String, Integer>builder()
                        .conditions(List.of(
                                BaseCondition.<String, Integer>builder()
                                        .compare("a")
                                        .with(lessThan)
                                        .to(5)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("b")
                                        .with(greaterThan)
                                        .to(5)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("c")
                                        .with(equal)
                                        .to(30)
                                        .build()))
                        .build())
                .expected(true)
                .build());
        testCases.put("6. SatisfyAny expect false", TestCase.builder()
                .getter((key) -> {
                    if (key == null) {
                        return 0;
                    }
                    return switch (key) {
                        case "a" -> 10;
                        case "b" -> 20;
                        case "c" -> 30;
                        default -> 0;
                    };
                })
                .condition(SatisfyAny.<String, Integer>builder()
                        .conditions(List.of(
                                BaseCondition.<String, Integer>builder()
                                        .compare("a")
                                        .with(lessThan)
                                        .to(5)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("b")
                                        .with(greaterThan)
                                        .to(20)
                                        .build(),
                                BaseCondition.<String, Integer>builder()
                                        .compare("c")
                                        .with(equal)
                                        .to(31)
                                        .build()))
                        .build())
                .expected(false)
                .build());

        testCases.put("7. Composite conditional expect true", TestCase.builder()
                .getter((key) -> {
                    if (key == null) {
                        return 0;
                    }
                    return switch (key) {
                        case "a" -> 10;
                        case "b" -> 20;
                        case "c" -> 30;
                        default -> 0;
                    };
                })
                .condition(SatisfyAny.<String, Integer>builder()
                        .conditions(List.of(SatisfyAll.<String, Integer>builder()
                                .conditions(List.of(
                                        BaseCondition.<String, Integer>builder()
                                                .compare("a")
                                                .with(lessThan)
                                                .to(5)
                                                .build(),
                                        BaseCondition.<String, Integer>builder()
                                                .compare("b")
                                                .with(greaterThan)
                                                .to(5)
                                                .build(),
                                        BaseCondition.<String, Integer>builder()
                                                .compare("c")
                                                .with(equal)
                                                .to(30)
                                                .build()))
                                .build(),
                                SatisfyAll.<String, Integer>builder()
                                        .conditions(List.of(
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("a")
                                                        .with(lessThan)
                                                        .to(20)
                                                        .build(),
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("b")
                                                        .with(greaterThan)
                                                        .to(5)
                                                        .build(),
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("c")
                                                        .with(equal)
                                                        .to(30)
                                                        .build()))
                                        .build()))
                        .build())
                .expected(true)
                .build());
        return testCases;
    }

}
