package ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCallPeriodGenerator {

    @Test
    public void test() {
        CallPeriodGenerator callPeriodGenerator = new CallPeriodGenerator();

        for (int i = 0; i < 12; i++) {
            Assertions.assertNotNull(callPeriodGenerator.generateRandomCallPeriod(i + 1));
        }
    }
}
