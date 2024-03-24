package ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCallTypeGenerator {

    @Test
    public void test() {
        CallTypeGenerator callTypeGenerator = new CallTypeGenerator();

        Assertions.assertNotNull(callTypeGenerator.generateRandomCallType());
    }
}
