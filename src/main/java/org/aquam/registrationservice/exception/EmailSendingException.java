package org.aquam.registrationservice.exception;

public class EmailSendingException extends RuntimeException {

    public EmailSendingException() {}

    public EmailSendingException(String message) {
        super(message);
    }
}
