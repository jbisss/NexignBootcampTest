package ru.nexigntestovoe.jbisss.file.writers;

import ru.nexigntestovoe.jbisss.generator.ApplicationConstants;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.domain.Cdr;
import ru.nexigntestovoe.jbisss.file.creators.FileCreator;

import java.util.concurrent.atomic.AtomicInteger;

public class CdrFilesWriter implements FilesWriter<Cdr> {

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    private final FileCreator<String> fileCreator;

    public CdrFilesWriter(FileCreator<String> fileCreator) {
        this.fileCreator = fileCreator;
    }

    @Override
    public void write(Cdr cdrToWrite) {
        fileCreator.createFile(createFileName(), cdrToWrite.toString());
    }

    private String createFileName() {
        String fileNameTemplate = ApplicationConstants.PATH_TO_CDRS + "cdr_%s.txt";
        return String.format(fileNameTemplate, atomicInteger.incrementAndGet());
    }
}
