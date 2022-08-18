package org.aquam.registrationservice.service;

import lombok.RequiredArgsConstructor;
import org.aquam.registrationservice.model.AppUser;
import org.aquam.registrationservice.model.AppUserRole;
import org.aquam.registrationservice.model.RegistrationRequest;
import org.aquam.registrationservice.repository.RegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationConfirmationService userValidationService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        if (registrationRepository.findByUsername(registrationRequest.getUsername()).isPresent())
            throw new EntityExistsException("username is taken");
        if (registrationRepository.findByEmail(registrationRequest.getEmail()).isPresent())
            throw new EntityExistsException("email exists");

        AppUser user = convertRequestToEntity(registrationRequest);
        user.setAppUserRole(AppUserRole.USER);
        user.setLocked(false);
        user.setEnabled(false);
        registrationRepository.save(user);

        // todo: generate sequence
        // String confirmationSequence = userValidationService.generateConfirmationSequence(user.getId());
        // todo: ask email service to send email with link
        // String confirmationLink = "http://localhost:8081/registration/confirm/" + confirmationSequence;

        return user.getEmail();
    }

    public AppUser convertRequestToEntity(@Valid RegistrationRequest registrationRequest) {
        Set<ConstraintViolation<RegistrationRequest>> validationExceptions = validator.validate(registrationRequest);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        AppUser user = modelMapper.map(registrationRequest, AppUser.class);
        return user;
    }

}
