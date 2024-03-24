package ru.nexigntestovoe.jbisss.file.writers;

import ru.nexigntestovoe.jbisss.generator.ApplicationConstants;
import ru.nexigntestovoe.jbisss.file.creators.FileCreator;
import ru.nexigntestovoe.jbisss.generator.udrGenerator.domain.Udr;

public class UdrFilesWriter implements FilesWriter<Udr> {

    private final FileCreator<String> fileCreator;

    public UdrFilesWriter(FileCreator<String> fileCreator) {
        this.fileCreator = fileCreator;
    }

    @Override
    public void write(Udr udr) {
        fileCreator.createFile(createUdrFileName(udr), udr.toString());
    }

    private String createUdrFileName(Udr udrFile) {
        return String.format(ApplicationConstants.PATH_TO_REPORTS + "%s_%s.json", udrFile.getMsisdn(), udrFile.getMonth());
    }
}
