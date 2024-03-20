package core.basesyntax.service.impl;

import core.basesyntax.exception.CantWriteToFileException;
import core.basesyntax.service.FileWriterService;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterImpl implements FileWriterService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String HEADER = "fruit,quantity";

    @Override
    public boolean write(List<String> processedData, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(HEADER);
            for (String data : processedData) {
                fileWriter.write(LINE_SEPARATOR);
                fileWriter.write(data);
            }
        } catch (IOException e) {
            throw new CantWriteToFileException("Can't write data to file:" + processedData);
        }
        return true;
    }
}