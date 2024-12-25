package utils.vinayak.patterns;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class ConditionalCompositeTest {
    @Test
    public void testGetSatisfiableOptionsWithMaintainingInputOrder() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            ConditionalComposite<Integer, Object, Object> conditionalComposite = new ConditionalComposite<>();
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
            List<Integer> actualOutput = conditionalComposite.getSatisfiableOptions(null);
            assertEquals(expectedOutput, actualOutput);
        }
    }

    @Test
    public void testGetFirstSatisfiableOption() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            ConditionalComposite<Integer, Object, Object> conditionalComposite = new ConditionalComposite<>();
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
            Integer actualOutput = conditionalComposite.getFirstSatisfiableOption(null);
            assertEquals(expectedOutput, actualOutput);
        }
    }
}
