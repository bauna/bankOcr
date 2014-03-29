package ar.com.bauna.bankOCR;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OcrParser {
    private final OcrParserEventHandler handler;

    public OcrParser(OcrParserEventHandler handler) {
        this.handler = handler;
    }

    public void parse(File file) throws FileNotFoundException, IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
            parse(in);
        }
    }

    public void parse(InputStream in) throws IOException {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        
        List<String> item;
        while ((item = readItem(reader)) != null) {
            int[] accountNumber = parseAccountNumber(item);
            try {
                handler.onAccount(accountNumber);
            } catch (Exception e) {
                throw new RuntimeException("Error handling account number at line: " + reader.getLineNumber(), e);
            }
        }
    }

    private int[] parseAccountNumber(List<String> item) {
        int[] digits = new int[9];
        
        for (int i = 0; i < 9; i++) {
            Character[][] digitCell = new Character[3][3];
            for (int col = 0; col < 3; col++) {
                digitCell[0][col] = item.get(0).charAt(i * 3 + col);
                digitCell[1][col] = item.get(1).charAt(i * 3 + col);
                digitCell[2][col] = item.get(2).charAt(i * 3 + col);
            }
            digits[i] = Digit.toInt(digitCell);
        }
        return digits;
    }

    private List<String> readItem(LineNumberReader reader) throws IOException {
        List<String> item = new ArrayList<String>(3);
        String line;
        if ((line = reader.readLine()) != null) {
            if (line.length() == 27) {
                item.add(line);
            } else {
                throw new RuntimeException("Wrong line format: " + reader.getLineNumber());
            }
        } else {
            return null;
        }
        if ((line = reader.readLine()) != null && line.length() == 27) {
            item.add(line);
        } else {
            throw new RuntimeException("Wrong line format: " + reader.getLineNumber());
        }
        if ((line = reader.readLine()) != null && line.length() == 27) {
            item.add(line);
        } else {
            throw new RuntimeException("Wrong line format: " + reader.getLineNumber());
        }
        reader.readLine();
        return item;
    }
}
