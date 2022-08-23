package org.aquam.registrationservice.service;

import org.aquam.registrationservice.model.AppUser;
import org.aquam.registrationservice.model.RegistrationRequest;

import javax.validation.Valid;


public interface RegistrationService {

    String register(RegistrationRequest registrationRequest);
    String confirmRegistration(String confirmationSequence);
    AppUser convertRequestToEntity(@Valid RegistrationRequest registrationRequest);
    void unconfirmedAccountsCleanup();
}
