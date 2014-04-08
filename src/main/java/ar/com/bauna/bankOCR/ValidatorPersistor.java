package ar.com.bauna.bankOCR;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import ar.com.bauna.bankOCR.AccountNumber.Validation;

public class ValidatorPersistor implements OcrParserEventHandler {

    private final OutputStream out;

    public ValidatorPersistor(OutputStream out) {
        this.out = out;
    }

    @Override
    public void onAccount(AccountNumber accountNumber) throws Exception {
    	out.write(accountNumber.asString().getBytes("UTF-8"));
    	out.write('\n');
    }
    
    @Override
    public void onAccountError(AccountNumber readAccount,
    		Set<AccountNumber> fixes, Validation validation) throws Exception {
    	switch (fixes.size()) {
		case 0:
			out.write(readAccount.asString().getBytes("UTF-8"));
			out.write(' ');
			out.write(validation.name().getBytes("UTF-8"));
			break;
		case 1:
			out.write(fixes.iterator().next().asString().getBytes("UTF-8"));
			break;
		default:
			out.write(readAccount.asString().getBytes("UTF-8"));
			out.write(' ');
			out.write('A');
			out.write('M');
			out.write('B');
			out.write(' ');
			out.write('[');
			out.write(listToString(fixes));
			out.write(']');
			break;
		}
    	out.write('\n');
    }
    
    private byte[] listToString(Set<AccountNumber> fixes) throws UnsupportedEncodingException {
    	StringBuilder s = new StringBuilder();
    	for (AccountNumber accountNumber : fixes) {
			s.append(", '").append(accountNumber.asString()).append("'");
		}
		return s.substring(2).getBytes("UTF-8");
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
