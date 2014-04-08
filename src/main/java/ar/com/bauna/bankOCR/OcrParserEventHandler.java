package ar.com.bauna.bankOCR;

import java.util.Set;

import ar.com.bauna.bankOCR.AccountNumber.Validation;

public interface OcrParserEventHandler {

    void onAccount(AccountNumber accountNumber) throws Exception;
    void onAccountError(AccountNumber readAccount, Set<AccountNumber> fixes, Validation validation) throws Exception;
}
