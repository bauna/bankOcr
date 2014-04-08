package ar.com.bauna.bankOCR;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

import ar.com.bauna.bankOCR.AccountNumber.Validation;


public class OcrParserTest {

    @Test
    public void testParsingWellDefinedAccounts() throws IOException {
        final Queue<String> vals = new LinkedList<String>(
                asList("123456789", "000000000",
                       "111111111", "222222222",
                       "333333333", "444444444",
                       "555555555", "666666666",
                       "777777777", "888888888",
                       "999999999"));
        OcrParser parser = new OcrParser(new OcrParserEventHandler() {

            @Override
            public void onAccount(AccountNumber accountNumber) {
                String accountNum = accountNumber.asString();
                String poll = vals.poll();
                assertEquals(poll, accountNum);
            }
            
            @Override
            public void onAccountError(AccountNumber readAccount,
            		Set<AccountNumber> fixes, Validation validation)
            		throws Exception {
            	String accountNum = readAccount.asString();
                String poll = vals.poll();
                assertEquals(poll, accountNum);
            }
        });

        parser.parse(OcrParser.class.getResourceAsStream("testParsingWellDefinedAccounts.txt"));
        assertEquals(0, vals.size());
    }

    @Test
    public void testParsingAccountsWithErros() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new OcrParser(new ValidatorPersistor(out)).parse(
                OcrParser.class.getResourceAsStream("testParsingAccountsWithErrors.txt"));
        out.flush();

        assertEquals("457508000\n664371485\n86110??36 ILL\n"
        		+ "888888888 AMB ['888886888', '888888988', '888888880']\n"
        		+ "?23456789 AMB ['423456709', '123456789']\n", out.toString());
    }

}
