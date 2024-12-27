package utils.vinayak.patterns.CompositeConditional;

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

}
