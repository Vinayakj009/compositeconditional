package utils.vinayak.patterns.CompositeConditional;

import java.util.Map;

import org.junit.jupiter.api.Test;

import lombok.NoArgsConstructor;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Condition;
import utils.vinayak.patterns.CompositeConditional.Interfaces.Operation;

public class JsonConfigParserTest {

    @NoArgsConstructor
    private static class equalOperation implements Operation<String> {
        @Override
        public boolean satisfies(String input, String compareTo) {
            return input.equals(compareTo);
        }
    }

    @NoArgsConstructor
    private static class notEqualOperation implements Operation<String> {
        @Override
        public boolean satisfies(String input, String compareTo) {
            return !input.equals(compareTo);
        }
    }

    @Test
    public void parseBaseConditonThroughString() {
        String config = "{\"className\": \"bc\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"eq\", \"data\":{}}}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);
        Condition<String, String> condition = parser.parseCondition(config);
        assert condition.satisfies(key -> {
            if (key.equals("subject")) {
                return "science";
            }
            return "";
        });
    }

    @Test
    public void parseCompositeCondition() {
        String config = "{\"className\": \"bc\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"ne\", \"data\":{}}}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("ne", notEqualOperation.class);
        Condition<String, String> condition = parser.parseCondition(config);
        assert condition.satisfies(key -> {
            if (!key.equals("subject")) {
                return "science";
            }
            return "";
        });
    }

    @Test
    public void parseSatisfyAllPassCondition() {
        String config = "{\"className\": \"satisfyAll\",\"data\": {\"conditions\": [" +
                "{\"className\": \"bc\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"eq\", \"data\":{}}}},"
                +
                "{\"className\": \"bc\",\"data\": {\"compare\": \"grade\",\"to\": \"A\",\"operation\": {\"className\": \"eq\", \"data\":{}}}}"
                +
                "]}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);
        parser.addClass("satisfyAll", SatisfyAll.class);
        Condition<String, String> condition = parser.parseCondition(config);
        assert condition.satisfies(key -> {
            if (key.equals("subject")) {
                return "science";
            } else if (key.equals("grade")) {
                return "A";
            }
            return null;
        });
    }

    @Test
    public void parseSatisfyAllFailCondition() {
        String config = "{\"className\": \"satisfyAll\",\"data\": {\"conditions\": [" +
                "{\"className\": \"bc\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"eq\", \"data\":{}}}},"
                +
                "{\"className\": \"bc\",\"data\": {\"compare\": \"grade\",\"to\": \"B\",\"operation\": {\"className\": \"ne\", \"data\":{}}}}"
                +
                "]}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);
        parser.addClass("ne", notEqualOperation.class);
        parser.addClass("satisfyAll", SatisfyAll.class);
        Condition<String, String> condition = parser.parseCondition(config);
        assert !condition.satisfies(key -> {
            if (key.equals("subject")) {
                return "science";
            } else if (key.equals("grade")) {
                return "B";
            }
            return null;
        });
    }

    @Test
    public void parseSatisfyAnyPassCondition() {
        String config = "{\"className\": \"satisfyAny\",\"data\": {\"conditions\": [" +
                "{\"className\": \"bc\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"eq\", \"data\":{}}}},"
                +
                "{\"className\": \"bc\",\"data\": {\"compare\": \"grade\",\"to\": \"B\",\"operation\": {\"className\": \"ne\", \"data\":{}}}}"
                +
                "]}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);
        parser.addClass("ne", notEqualOperation.class);
        parser.addClass("satisfyAny", SatisfyAny.class);
        Condition<String, String> condition = parser.parseCondition(config);
        assert condition.satisfies(key -> {
            if (key.equals("subject")) {
                return "science";
            } else if (key.equals("grade")) {
                return "B";
            }
            return null;
        });
    }

    @Test
    public void parseConditionsMultipleBaseConditions() {
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);

        Map<String, Object> data = new java.util.HashMap<>();
        data.put("conditions", java.util.Arrays.asList(
                java.util.Map.of("className", "bc", "data",
                        java.util.Map.of("compare", "subject", "to", "science",
                                "operation", java.util.Map.of("className", "eq", "data", java.util.Map.of()))),
                java.util.Map.of("className", "bc", "data",
                        java.util.Map.of("compare", "grade", "to", "A",
                                "operation", java.util.Map.of("className", "eq", "data", java.util.Map.of())))));

        java.util.List<Condition<String, String>> conditions = new java.util.ArrayList<>();
        parser.parseConditions(data, conditions::add);

        assert conditions.size() == 2;
        assert conditions.get(0).satisfies(key -> key.equals("subject") ? "science" : "");
        assert conditions.get(1).satisfies(key -> key.equals("grade") ? "A" : "");
    }

    @Test
    public void testParseConditionClassNotFoundException() {
        String config = "{\"className\": \"unknownClass\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"eq\", \"data\":{}}}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);
        try {
            parser.parseCondition(config);
            assert false;
        } catch (RuntimeException e) {
            assert e.getMessage().equals(Constants.EXCEPTION_CLASS_NOT_FOUND);
        }
        Map<String, Object> objectconfig = new java.util.HashMap<>();
        objectconfig.put("className", "unknownClass");
        objectconfig.put("data", java.util.Map.of("compare", "subject", "to", "science",
                "operation", java.util.Map.of("className", "eq", "data", java.util.Map.of())));
        Map<String, Object> objectconfigs = new java.util.HashMap<>();
        objectconfigs.put("conditions", java.util.Arrays.asList(objectconfig));
        try {
            parser.parseConditions(objectconfigs, (condition) -> {
            });
            assert false;
        } catch (RuntimeException e) {
            assert e.getMessage().equals(Constants.EXCEPTION_CLASS_NOT_FOUND);
        }
    }
    @Test
    public void testParseConditionClassDoesNotImplementConfigParserException() {
        String config = "{\"className\": \"eq\",\"data\": {\"compare\": \"subject\",\"to\": \"science\",\"operation\": {\"className\": \"eq\", \"data\":{}}}}";
        JsonConfigParser<String, String> parser = new JsonConfigParser<>();
        parser.addClass("bc", BaseCondition.class);
        parser.addClass("eq", equalOperation.class);
        try {
            parser.parseCondition(config);
            assert false;
        } catch (RuntimeException e) {
            assert e.getMessage().equals(Constants.EXCEPTION_CLASS_DOES_NOT_IMPLEMENT);
        }
    }

}
