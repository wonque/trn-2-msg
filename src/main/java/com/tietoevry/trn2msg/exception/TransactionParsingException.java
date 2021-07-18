package com.tietoevry.trn2msg.exception;

public class TransactionParsingException extends RuntimeException {

    public TransactionParsingException(String message) {
        super(message);
    }

    public TransactionParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
