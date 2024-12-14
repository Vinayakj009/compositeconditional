package utils.vinayak.patterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import lombok.Builder;

public class StudentSegregatorTest {
    @Builder
    private static class GradingOption {
        private final String grade;
        private final Condition<String, Integer> condition;
    }

    @Builder
    private static class TestCase {
        private final Getter<String, Integer> studentScore;
        private final List<GradingOption> gradingOptions;
        private final String expectedGrade;
    }

    @ParameterizedTest
    @MethodSource("getTestCaseNames")
    public void testCompositeConditionals(String testName) {
        TestCase testCase = getTestCases().get(testName);
        for (GradingOption gradingOption : testCase.gradingOptions) {
            if (gradingOption.condition.satisfies(testCase.studentScore)) {
                assertEquals(testCase.expectedGrade, gradingOption.grade);
                return;
            }
        }
        fail(testName);
    }

    private static List<String> getTestCaseNames() {
        return getTestCases().keySet().stream().sorted().toList();
    }

    private static Map<String, TestCase> getTestCases() {

        List<GradingOption> gradingOptions = getGradingOptons();
        Map<String, TestCase> testCases = new HashMap<>();
        testCases.put("1. topper", TestCase.builder()
                .studentScore((subject) -> {
                    return switch (subject) {
                        case "science" -> 100;
                        case "maths" -> 100;
                        default -> 0;
                    };
                })
                .gradingOptions(gradingOptions)
                .expectedGrade("topper")
                .build());
        testCases.put("2. A++ with 100 in science", TestCase.builder()
                .studentScore((subject) -> {
                    return switch (subject) {
                        case "science" -> 100;
                        case "maths" -> 98;
                        default -> 0;
                    };
                })
                .gradingOptions(gradingOptions)
                .expectedGrade("A++")
                .build());
        testCases.put("3. A++ with 100 in maths", TestCase.builder()
                .studentScore((subject) -> {
                    return switch (subject) {
                        case "science" -> 96;
                        case "maths" -> 100;
                        default -> 0;
                    };
                })
                .gradingOptions(gradingOptions)
                .expectedGrade("A++")
                .build());

        testCases.put("4. A+ with 100 in science", TestCase.builder()
                .studentScore((subject) -> {
                    return switch (subject) {
                        case "science" -> 100;
                        case "maths" -> 93;
                        default -> 0;
                    };
                })
                .gradingOptions(gradingOptions)
                .expectedGrade("A+")
                .build());
        testCases.put("5. A+ ", TestCase.builder()
                .studentScore((subject) -> {
                    return switch (subject) {
                        case "science" -> 96;
                        case "maths" -> 98;
                        default -> 0;
                    };
                })
                .gradingOptions(gradingOptions)
                .expectedGrade("A+")
                .build());
        testCases.put("6. A+ with 100 in maths", TestCase.builder()
                .studentScore((subject) -> {
                    return switch (subject) {
                        case "science" -> 92;
                        case "maths" -> 100;
                        default -> 0;
                    };
                })
                .gradingOptions(gradingOptions)
                .expectedGrade("A+")
                .build());
        return testCases;
    }

    private static List<GradingOption> getGradingOptons() {
        Operation<Integer> equal = (a, b) -> a.equals(b);
        Operation<Integer> greaterThan = (a, b) -> a > b;
        return List.of(
                GradingOption.builder()
                        .grade("topper")
                        .condition(SatisfyAll.<String, Integer>builder()
                                .conditions(List.of(
                                        BaseCondition.<String, Integer>builder()
                                                .compare("science")
                                                .with(equal)
                                                .to(100)
                                                .build(),
                                        BaseCondition.<String, Integer>builder()
                                                .compare("maths")
                                                .with(equal)
                                                .to(100)
                                                .build()))
                                .build())
                        .build(),

                GradingOption.builder()
                        .grade("A++")
                        .condition(SatisfyAny.<String, Integer>builder()
                                .conditions(List.of(SatisfyAll.<String, Integer>builder()
                                        .conditions(List.of(
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("science")
                                                        .with(greaterThan)
                                                        .to(95)
                                                        .build(),
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("maths")
                                                        .with(equal)
                                                        .to(100)
                                                        .build()))
                                        .build(),
                                        SatisfyAll.<String, Integer>builder()
                                                .conditions(List.of(
                                                        BaseCondition.<String, Integer>builder()
                                                                .compare("science")
                                                                .with(equal)
                                                                .to(100)
                                                                .build(),
                                                        BaseCondition.<String, Integer>builder()
                                                                .compare("maths")
                                                                .with(greaterThan)
                                                                .to(95)
                                                                .build()))
                                                .build()))
                                .build())
                        .build(),
                GradingOption.builder()
                        .grade("A+")
                        .condition(SatisfyAny.<String, Integer>builder()
                                .conditions(List.of(SatisfyAll.<String, Integer>builder()
                                        .conditions(List.of(
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("science")
                                                        .with(greaterThan)
                                                        .to(90)
                                                        .build(),
                                                BaseCondition.<String, Integer>builder()
                                                        .compare("maths")
                                                        .with(equal)
                                                        .to(100)
                                                        .build()))
                                        .build(),
                                        SatisfyAll.<String, Integer>builder()
                                                .conditions(List.of(
                                                        BaseCondition.<String, Integer>builder()
                                                                .compare("science")
                                                                .with(equal)
                                                                .to(100)
                                                                .build(),
                                                        BaseCondition.<String, Integer>builder()
                                                                .compare("maths")
                                                                .with(greaterThan)
                                                                .to(90)
                                                                .build()))
                                                .build(),
                                        SatisfyAll.<String, Integer>builder()
                                                .conditions(List.of(
                                                        BaseCondition.<String, Integer>builder()
                                                                .compare("science")
                                                                .with(greaterThan)
                                                                .to(95)
                                                                .build(),
                                                        BaseCondition.<String, Integer>builder()
                                                                .compare("maths")
                                                                .with(greaterThan)
                                                                .to(95)
                                                                .build()))
                                                .build()))
                                .build())
                        .build());
    }
}
