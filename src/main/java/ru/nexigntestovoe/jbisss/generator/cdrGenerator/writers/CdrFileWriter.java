package ru.nexigntestovoe.jbisss.generator.cdrGenerator.writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CdrFileWriter implements StringsWriter {

    @Override
    public void write(List<List<String>> stringToWrite) {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (List<String> listStringCdr : stringToWrite) {
            writeListString(listStringCdr, atomicInteger);
        }
    }

    private void writeListString(List<String> listString, AtomicInteger atomicInteger) {
        String fileNameTemplate = ".\\src\\main\\resources\\cdrs\\cdr_%s.txt";
        String fileName = String.format(fileNameTemplate, atomicInteger.incrementAndGet());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : listString) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
