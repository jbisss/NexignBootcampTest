package ru.nexigntestovoe.jbisss;

import ru.nexigntestovoe.jbisss.generator.cdrGenerator.CdrGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.domain.Cdr;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallPeriodGenerator;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.CallTypeGenerator;
import ru.nexigntestovoe.jbisss.generator.udrGenerator.domain.Udr;
import ru.nexigntestovoe.jbisss.file.writers.CdrFilesWriter;
import ru.nexigntestovoe.jbisss.file.creators.FileCreator;
import ru.nexigntestovoe.jbisss.file.creators.StringFileCreator;
import ru.nexigntestovoe.jbisss.h2db.SqlAdapterH2;
import ru.nexigntestovoe.jbisss.generator.udrGenerator.UdrGenerator;
import ru.nexigntestovoe.jbisss.file.readers.CdrFileReader;
import ru.nexigntestovoe.jbisss.file.readers.StringReader;
import ru.nexigntestovoe.jbisss.file.writers.FilesWriter;
import ru.nexigntestovoe.jbisss.file.writers.UdrFilesWriter;

public class Main {

    private static final CdrGenerator cdrGenerator;
    private static final UdrGenerator udrGenerator;

    static {
        CallPeriodGenerator callPeriodGenerator = new CallPeriodGenerator();
        SqlAdapterH2 sqlAdapter = new SqlAdapterH2();
        CallTypeGenerator callTypeGenerator = new CallTypeGenerator();

        FileCreator<String> fileCreatorCdr = new StringFileCreator();
        FileCreator<String> fileCreatorUdr = new StringFileCreator();
        FilesWriter<Cdr> filesWriterCdr = new CdrFilesWriter(fileCreatorCdr);
        FilesWriter<Udr> filesWriterUdr = new UdrFilesWriter(fileCreatorUdr);

        StringReader<Cdr> stringReader = new CdrFileReader();

        cdrGenerator = new CdrGenerator(sqlAdapter, callPeriodGenerator, callTypeGenerator, filesWriterCdr, sqlAdapter);
        udrGenerator = new UdrGenerator(stringReader, filesWriterUdr);
    }

    public static void main(String[] args) {
        cdrGenerator.generateCdrs();
        udrGenerator.generateReports();
        // udrGenerator.generateReports("79217565350");
        // udrGenerator.generateReports("79217565350", "12");
    }
}
