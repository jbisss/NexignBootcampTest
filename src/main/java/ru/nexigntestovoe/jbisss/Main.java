package ru.nexigntestovoe.jbisss;

import ru.nexigntestovoe.jbisss.generator.cdrGenerator.CdrGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallPeriodGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallTypeGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.PhoneGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.writers.CdrFileWriter;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.writers.StringsWriter;
import ru.nexigntestovoe.jbisss.generator.h2db.PhoneNumbersSource;
import ru.nexigntestovoe.jbisss.generator.h2db.SqlAdapterH2;
import ru.nexigntestovoe.jbisss.generator.udrGenerator.UdrGenerator;

public class Main {

    private static final CdrGenerator cdrGenerator;
    private static final UdrGenerator udrGenerator;

    static {
        CallPeriodGenerator callPeriodGenerator = new CallPeriodGenerator();
        PhoneGenerator phoneGenerator = new PhoneGenerator();
        PhoneNumbersSource sqlAdapter = new SqlAdapterH2(phoneGenerator);
        CallTypeGenerator callTypeGenerator = new CallTypeGenerator();
        StringsWriter stringsWriter = new CdrFileWriter();

        cdrGenerator = new CdrGenerator(callPeriodGenerator, callTypeGenerator, stringsWriter, sqlAdapter);
        udrGenerator = new UdrGenerator();
    }

    public static void main(String[] args) {
        cdrGenerator.generateCdrs();
        udrGenerator.generateUdrs();
    }
}
