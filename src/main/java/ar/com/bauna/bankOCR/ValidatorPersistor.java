package ar.com.bauna.bankOCR;

import java.io.IOException;
import java.io.OutputStream;

public class ValidatorPersistor implements OcrParserEventHandler {

    private final OutputStream out;

    public ValidatorPersistor(OutputStream out) {
        this.out = out;
    }

    @Override
    public void onAccount(int[] accountNumber) throws IOException {
        validate(accountNumber);
    }

    private void validate(int[] accountNumber) throws IOException {
        boolean isIllegal = false;
        int checksum = 0;
        for (int i = 0; i < accountNumber.length; i++) {
            int digit = accountNumber[i];
            checksum += digit * (9 - i);
            if (digit == -1) {
                isIllegal = true;
                out.write('?');
            } else {
                out.write((char) (0x30 + digit));
            }
        }
        if (isIllegal) {
            out.write(' ');
            out.write('I');
            out.write('L');
            out.write('L');
        } else if (checksum % 11 != 0) {
            out.write(' ');
            out.write('E');
            out.write('R');
            out.write('R');
        }
        out.write('\n');
    }
}
