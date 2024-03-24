package ru.nexigntestovoe.jbisss.file.readers;

import ru.nexigntestovoe.jbisss.generator.ApplicationConstants;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.domain.Cdr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CdrFileReader implements StringReader<Cdr> {

    @Override
    public List<Cdr> read() {
        List<Cdr> result = new ArrayList<>();

        File directory = new File(ApplicationConstants.PATH_TO_CDRS);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : Objects.requireNonNull(files)) {
                if (file.isFile()) {
                    Cdr currentCdr = new Cdr();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] tokens = line.split(ApplicationConstants.COMMA_DELIMITER);
                            currentCdr.addRow(tokens[0], tokens[1], Long.parseLong(tokens[2]), Long.parseLong(tokens[3]));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    result.add(currentCdr);
                }
            }
        } else {
            System.out.println("Such directory doesn't exist!");
        }
        return result;
    }
}
