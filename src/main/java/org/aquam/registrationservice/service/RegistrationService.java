package org.aquam.registrationservice.service;

import org.aquam.registrationservice.model.AppUser;
import org.aquam.registrationservice.model.RegistrationRequest;

import javax.validation.Valid;


public interface RegistrationService {

    String register(RegistrationRequest registrationRequest);
    AppUser saveUser(AppUser user);
    String confirmRegistration(String confirmationSequence);
    Boolean checkIfUserValid(AppUser user);
    AppUser convertRequestToEntity(@Valid RegistrationRequest registrationRequest);
    void unconfirmedAccountsCleanup();
}
