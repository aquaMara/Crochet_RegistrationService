package org.aquam.registrationservice.service;

import org.aquam.registrationservice.model.ConfirmationData;

public interface RegistrationConfirmationService {

    String sendConfirmationSequence(Long id, String email);
    String makeApiCall(ConfirmationData confirmationData);
    String generateConfirmationSequence(Long id);
    Long decodeConfirmationSequence(String confirmationSequence);
}
