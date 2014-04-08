package ar.com.bauna.bankOCR;

import static ar.com.bauna.bankOCR.AccountNumber.Validation.ERR;
import static ar.com.bauna.bankOCR.AccountNumber.Validation.ILL;
import static ar.com.bauna.bankOCR.AccountNumber.Validation.OK;

import java.util.Arrays;

public class AccountNumber {
	public enum Validation {OK, ERR, ILL}
	
	private final Digit[] account = new Digit[9];

	public AccountNumber(Digit[] account) {
		for (int i = 0; i < this.account.length; i++) {
			this.account[i] = account[i];
		}
	}

	public Validation isValid() {
		int checksum = 0;
		for (int i = 0; i < account.length; i++) {
            int digit = Digit.toInt(account[i]);
            if (digit == -1) {
            	return ILL;
            }
            checksum += digit * (9 - i);
        }
		return checksum % 11 == 0 ? OK : ERR;
	}
	
	public String asString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < account.length; i++) {
			int digit = Digit.toInt(account[i]);
			s.append(digit == -1 ? '?' : (char) (0x30 + digit)); 
		}
		return s.toString();
	}
	
	@Override
	public String toString() {
		return asString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(account);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountNumber other = (AccountNumber) obj;
		if (!Arrays.equals(account, other.account))
			return false;
		return true;
	}
	
	
}
