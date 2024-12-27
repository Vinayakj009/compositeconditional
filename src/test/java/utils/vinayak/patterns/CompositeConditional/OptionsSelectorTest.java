package utils.vinayak.patterns.CompositeConditional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class OptionsSelectorTest {
    @Test
    public void testGetSatisfiableOptionsWithMaintainingInputOrder() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            OptionsSelector<Integer, Object, Object> conditionalComposite = new OptionsSelector<>();
            List<Integer> expectedOutput = new ArrayList<>();
            for (int j = 0; j < 50000; j++) {
                int randomInt = random.nextInt(1000000);
                if (conditionalComposite.containsKey(randomInt)) {
                    continue;
                }
                boolean expectedInOutput = random.nextBoolean();
                if (expectedInOutput) {
                    expectedOutput.add(randomInt);
                }
                conditionalComposite.put(randomInt, data -> expectedInOutput);
            }
            List<Integer> actualOutput = conditionalComposite.getSelectableOptions(null);
            assertEquals(expectedOutput, actualOutput);
        }
    }

    @Test
    public void testGetFirstSatisfiableOption() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            OptionsSelector<Integer, Object, Object> conditionalComposite = new OptionsSelector<>();
            Integer expectedOutput = null;
            for (int j = 0; j < 50000; j++) {
                int randomInt = random.nextInt(1000000);
                if (conditionalComposite.containsKey(randomInt)) {
                    continue;
                }
                boolean expectedInOutput = random.nextBoolean();
                if (expectedInOutput && expectedOutput == null) {
                    expectedOutput = randomInt;
                }
                conditionalComposite.put(randomInt, data -> expectedInOutput);
            }
            Integer actualOutput = conditionalComposite.selectFirstViableOption(null);
            assertEquals(expectedOutput, actualOutput);
        }
    }
}
