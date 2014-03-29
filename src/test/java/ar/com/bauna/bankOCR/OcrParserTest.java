package ar.com.bauna.bankOCR;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class OcrParserTest {

    private String asString(int[] accountNumber) {
        String s = "";
        for (int digit : accountNumber) {
            s += digit;
        }
        return s;
    }

    @Test
    public void test() throws IOException {
        final Queue<String> vals = new LinkedList<String>(
                asList("123456789", "000000000",
                       "111111111", "222222222",
                       "333333333", "444444444",
                       "555555555", "666666666",
                       "777777777", "888888888",
                       "999999999"));
        OcrParser parser = new OcrParser(new OcrParserEventHandler() {

            @Override
            public void onAccount(int[] accountNumber) {
                String accountNum = asString(accountNumber);
                String poll = vals.poll();
                assertEquals(poll, accountNum);
            }
        });

        parser.parse(OcrParser.class.getResourceAsStream("test.txt"));
        assertEquals(0, vals.size());
    }

}
