package ru.nexigntestovoe.jbisss.file.creators;

public interface FileCreator<T> {

    void createFile(String fileName, T source);
}
