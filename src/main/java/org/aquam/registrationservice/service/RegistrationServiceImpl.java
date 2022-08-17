package org.aquam.registrationservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aquam.registrationservice.model.NewUser;
import org.aquam.registrationservice.model.RegistrationRequest;
import org.aquam.registrationservice.repository.RegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.validation.*;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;
    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public String register(RegistrationRequest registrationRequest) {
        if (registrationRepository.findNewUsersByUsername(registrationRequest.getUsername()).isPresent())
            throw new EntityExistsException("username is taken");
        if (registrationRepository.findNewUsersByEmail(registrationRequest.getEmail()).isPresent())
            throw new EntityExistsException("email exists");

        NewUser newUser = toUser(registrationRequest);
        registrationRepository.save(newUser);

        return newUser.getEmail();
    }

    public NewUser toUser(@Valid RegistrationRequest registrationRequest) {
        Set<ConstraintViolation<RegistrationRequest>> validationExceptions = validator.validate(registrationRequest);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        NewUser newUser = modelMapper.map(registrationRequest, NewUser.class);
        return newUser;
    }

}
