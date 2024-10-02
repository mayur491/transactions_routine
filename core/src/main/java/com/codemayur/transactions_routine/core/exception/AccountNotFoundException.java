package com.codemayur.transactions_routine.core.exception;

public class AccountNotFoundException extends AccountException {

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
