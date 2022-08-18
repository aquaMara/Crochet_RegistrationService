package org.aquam.registrationservice.service;

import org.aquam.registrationservice.model.RegistrationRequest;
import org.springframework.stereotype.Service;


public interface RegistrationService {
    String register(RegistrationRequest registrationRequest);
}
