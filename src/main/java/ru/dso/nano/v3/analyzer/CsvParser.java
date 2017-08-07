package ru.dso.nano.v3.analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    private static final String HEADER_START = "TRACK";

    public static OscillogramData parseData(File csvFile) throws IOException {
        List<Integer> data = new ArrayList<>(4096);
        List<String> lines = Files.readAllLines(csvFile.toPath());
        for(String line : lines.subList(1, lines.size() - 1)) {
            line = line.trim().replace(",", "");
            if(!line.isEmpty()) {
                try {
                    int value = Integer.parseInt(line, 10);
                    data.add(value);
                } catch(NumberFormatException nfe) {
                    System.err.println("\"" + line + "\" is not a valid integer");
                }
            }
        }
        return new OscillogramData(data.stream().mapToInt(i->i).toArray());
    }
}
