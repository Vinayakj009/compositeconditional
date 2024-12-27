package utils.vinayak.patterns.CompositeConditional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import lombok.Builder;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Getter;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Operation;

public class StudentSegregatorTest {

    @Builder
    private static class TestCase {
        private final Getter<String, Integer> studentScore;
        private final OptionsSelector<String, String, Integer> gradingOptions;
        private final String expectedGrade;
    }

    @ParameterizedTest
    @MethodSource("getTestCaseNames")
    public void testCompositeConditionals(String testName) {
        TestCase testCase = getTestCases().get(testName);
        String actualGrade = testCase.gradingOptions.selectFirstViableOption(testCase.studentScore);
        assertEquals(testCase.expectedGrade, actualGrade);
    }

    private static List<String> getTestCaseNames() {
        return getTestCases().keySet().stream().sorted().toList();
    }

    private static Map<String, TestCase> getTestCases() {

        OptionsSelector<String, String, Integer> gradingOptions = getGradingOptons();
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

    private static OptionsSelector<String, String, Integer> getGradingOptons() {
        OptionsSelector<String, String, Integer> gradingOptions = new OptionsSelector<>();
        Operation<Integer> equal = (a, b) -> a.equals(b);
        Operation<Integer> greaterThan = (a, b) -> a > b;
        gradingOptions.put("topper", SatisfyAll.<String, Integer>builder()
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
                .build());

        gradingOptions.put("A++", SatisfyAny.<String, Integer>builder()
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
                .build());
        gradingOptions.put("A+", SatisfyAny.<String, Integer>builder()
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
                .build());
        return gradingOptions;
    }
}
