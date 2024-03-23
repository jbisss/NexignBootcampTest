package ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators;

import java.util.Random;

public class PhoneGenerator {

    private static final int PHONE_NUMBER_LEN = 11;
    private static final int PHONE_NUMBER_BOUND = 10;
    private static final String PHONE_START_AT = "7";

    public String generateRandomPhoneNumber() {
        StringBuilder stringBuilder = new StringBuilder(PHONE_START_AT);
        for (int i = PHONE_START_AT.length(); i < PHONE_NUMBER_LEN; i++) {
            stringBuilder.append(new Random().nextInt(PHONE_NUMBER_BOUND));
        }
        return stringBuilder.toString();
    }
}
