package ar.com.bauna.bankOCR;

public interface OcrParserEventHandler {

    void onAccount(int[] accountNumber) throws Exception;

}
