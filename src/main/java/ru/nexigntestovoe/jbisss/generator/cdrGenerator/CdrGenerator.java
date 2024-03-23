package ru.nexigntestovoe.jbisss.generator.cdrGenerator;

import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallPeriodGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallTypeGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.writers.StringsWriter;
import ru.nexigntestovoe.jbisss.generator.h2db.PhoneNumbersSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CdrGenerator {

    private static final int PERIODS_TO_GENERATE = 1;
    private static final int FILES_IN_SINGLE_PERIOD = 12;
    private static final int ROWS_IN_SINGLE_FILE = 50;

    private static final String DELIMITER = ",";

    private final CallPeriodGenerator callPeriodGenerator;
    private final CallTypeGenerator callTypeGenerator;
    private final StringsWriter stringsWriter;

    private final PhoneNumbersSource phoneNumbersSource;

    public CdrGenerator(CallPeriodGenerator callPeriodGenerator, CallTypeGenerator callTypeGenerator, StringsWriter stringsWriter, PhoneNumbersSource phoneNumbersSource) {
        this.callPeriodGenerator = callPeriodGenerator;
        this.callTypeGenerator = callTypeGenerator;
        this.stringsWriter = stringsWriter;

        this.phoneNumbersSource = phoneNumbersSource;
    }

    public void generateCdrs() {
        List<List<String>> cdrs = new ArrayList<>();
        for (int i = 0; i < PERIODS_TO_GENERATE; i++) {
            for (int j = 0; j < FILES_IN_SINGLE_PERIOD; j++) {
                List<String> cdrRows = new ArrayList<>();
                for (int q = 0; q < ROWS_IN_SINGLE_FILE; q++) {
                    cdrRows.add(formSingleRowForCdr(j + 1));
                }
                cdrs.add(cdrRows);
            }
        }
        stringsWriter.write(cdrs);
    }

    private String formSingleRowForCdr(int monthNumber) {
        return callTypeGenerator.generateRandomCallType() +
                DELIMITER +
                chooseNumber() +
                DELIMITER +
                callPeriodGenerator.generateRandomCallPeriod(monthNumber);
    }

    private String chooseNumber() {
        List<String> allPhoneNumbers = phoneNumbersSource.getAllPhoneNumbers();
        return allPhoneNumbers.get(new Random().nextInt(allPhoneNumbers.size()));
    }
}
