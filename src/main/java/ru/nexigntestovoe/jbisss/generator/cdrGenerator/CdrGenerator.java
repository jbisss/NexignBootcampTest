package ru.nexigntestovoe.jbisss.generator.cdrGenerator;

import ru.nexigntestovoe.jbisss.generator.ApplicationConstants;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.domain.Cdr;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallPeriodGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallTypeGenerator;
import ru.nexigntestovoe.jbisss.h2db.PhoneNumbersSource;
import ru.nexigntestovoe.jbisss.h2db.SqlAdapterH2;
import ru.nexigntestovoe.jbisss.h2db.Transaction;
import ru.nexigntestovoe.jbisss.file.writers.FilesWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator of CDR-files
 */
public class CdrGenerator implements ICdrGenerator {

    private static final int PERIODS_TO_GENERATE = 1;
    private static final int FILES_IN_SINGLE_PERIOD = 12;
    private static final int ROWS_IN_SINGLE_FILE = 50;

    private final SqlAdapterH2 sqlAdapterH2;

    private final CallPeriodGenerator callPeriodGenerator;
    private final CallTypeGenerator callTypeGenerator;
    private final FilesWriter<Cdr> cdrFilesWriter;

    private final PhoneNumbersSource phoneNumbersSource;

    public CdrGenerator(SqlAdapterH2 sqlAdapterH2, CallPeriodGenerator callPeriodGenerator, CallTypeGenerator callTypeGenerator, FilesWriter<Cdr> cdrFilesWriter, PhoneNumbersSource phoneNumbersSource) {
        this.sqlAdapterH2 = sqlAdapterH2;
        this.callPeriodGenerator = callPeriodGenerator;
        this.callTypeGenerator = callTypeGenerator;
        this.cdrFilesWriter = cdrFilesWriter;

        this.phoneNumbersSource = phoneNumbersSource;
    }

    /**
     * Method which generates all CDR-files
     */
    @Override
    public void generateCdrs() {
        List<Cdr> cdrs = new ArrayList<>();
        for (int i = 0; i < PERIODS_TO_GENERATE; i++) {
            for (int j = 0; j < FILES_IN_SINGLE_PERIOD; j++) {
                Cdr currentCdr = new Cdr();
                for (int q = 0; q < ROWS_IN_SINGLE_FILE; q++) {
                    String[] callPeriodTokens = callPeriodGenerator.generateRandomCallPeriod(j + 1).split(ApplicationConstants.COMMA_DELIMITER);
                    String randomCallType = callTypeGenerator.generateRandomCallType();
                    String chosenNumber = chooseNumber();
                    long startCall = Long.parseLong(callPeriodTokens[0]);
                    long endCall = Long.parseLong(callPeriodTokens[1]);
                    currentCdr.addRow(randomCallType, chosenNumber, startCall, endCall);
                    sqlAdapterH2.addTransaction(new Transaction(randomCallType, callPeriodTokens[0], callPeriodTokens[1], sqlAdapterH2.getIdByPhoneNumber(chosenNumber)));
                }
                cdrs.add(currentCdr);
            }
        }
        cdrs.forEach(cdrFilesWriter::write);
        sqlAdapterH2.printAllTransactions();
    }

    private String chooseNumber() {
        List<String> allPhoneNumbers = phoneNumbersSource.getAllPhoneNumbers();
        return allPhoneNumbers.get(new Random().nextInt(allPhoneNumbers.size()));
    }
}
