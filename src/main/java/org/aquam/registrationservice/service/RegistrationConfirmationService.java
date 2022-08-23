package org.aquam.registrationservice.service;

public interface RegistrationConfirmationService {

    String sendConfirmationSequence(Long id, String email);
    String generateConfirmationSequence(Long id);
    Long decodeConfirmationSequence(String confirmationSequence);
}
