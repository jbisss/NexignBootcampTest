package ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators;

import java.util.Random;

public class CallTypeGenerator {

    private static final int CALL_TYPE_BOUND = 2;

    public String generateRandomCallType() {
        return String.valueOf(new Random().nextInt(CALL_TYPE_BOUND) + 1);
    }
}
