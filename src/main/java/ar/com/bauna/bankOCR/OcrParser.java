package ar.com.bauna.bankOCR;

import static ar.com.bauna.bankOCR.AccountNumber.Validation.OK;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.com.bauna.bankOCR.AccountNumber.Validation;

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
            Digit[] accountNumber = parseAccountNumber(item);
            try {
            	AccountNumber acctNumber = new AccountNumber(accountNumber);
            	Validation valid = acctNumber.isValid();
				if (valid == OK) {
            		handler.onAccount(acctNumber);
            	} else {
            		Set<AccountNumber> fixes = new LinkedHashSet<>();
            		fixDigits(accountNumber, fixes);
            		handler.onAccountError(acctNumber, fixes, valid);
            	}
            } catch (Exception e) {
                throw new RuntimeException("Error handling account number at line: " + reader.getLineNumber(), e);
            }
        }
    }

    private void fixDigits(Digit[] accountNumber, Set<AccountNumber> validAccounts) {
    	
    	char[] options = new char[] {' ', '|', '_'};
    	for (int i = 0; i < accountNumber.length; i++) {
    		Digit digit = accountNumber[i];
    		for (int row = 0; row < 3; row++) {
    			for (int col = 0; col < 3; col++) {
    				for (int opts = 0; opts < options.length; opts++) {
    					Digit newDigit = digit.changeCharAt(row, col, options[opts]);
    					if (newDigit.isValid()) {
    						accountNumber[i] = newDigit;
	    					AccountNumber newAccount = new AccountNumber(accountNumber);
	    					if (newAccount.isValid() == OK) {
	    						validAccounts.add(newAccount);
	    					}
	    					//when original original digit is invalid we need to recurse
	    					//in order to validate other existing illegal numbers 
	    					if (!digit.isValid()) { 
	    						fixDigits(accountNumber, validAccounts);
	    					}
    					}
    				}
    			}
    		}
    		accountNumber[i] = digit;
    	}
	}

	private Digit[] parseAccountNumber(List<String> item) {
        Digit[] digits = new Digit[9];
        for (int i = 0; i < 9; i++) {
            Character[][] digitCell = new Character[3][3];
            for (int col = 0; col < 3; col++) {
                digitCell[0][col] = item.get(0).charAt(i * 3 + col);
                digitCell[1][col] = item.get(1).charAt(i * 3 + col);
                digitCell[2][col] = item.get(2).charAt(i * 3 + col);
            }
            digits[i] = new Digit(digitCell);
            
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
