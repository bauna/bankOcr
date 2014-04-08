package ar.com.bauna.bankOCR;

import java.util.List;

import ar.com.bauna.bankOCR.AccountNumber.Validation;

public interface OcrParserEventHandler {

    void onAccount(AccountNumber accountNumber) throws Exception;
    void onAccountError(AccountNumber readAccount, List<AccountNumber> fixes, Validation validation) throws Exception;
}
