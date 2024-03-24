package ru.nexigntestovoe.jbisss.file.creators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for creating and filling files by strings
 */
public class StringFileCreator implements FileCreator<String>{

    @Override
    public void createFile(String fileName, String source) {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
