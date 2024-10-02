package com.codemayur.transactions_routine.core.exception;

public class TransactionNotFoundException extends TransactionException {

    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
