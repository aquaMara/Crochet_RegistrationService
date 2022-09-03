package org.aquam.registrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.registrationservice.config.ApplicationProperties;
import org.aquam.registrationservice.model.AppUser;
import org.aquam.registrationservice.model.AppUserRole;
import org.aquam.registrationservice.model.RegistrationRequest;
import org.aquam.registrationservice.repository.RegistrationRepository;
import org.aquam.registrationservice.service.RegistrationConfirmationService;
import org.aquam.registrationservice.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RegistrationConfirmationService registrationConfirmationService;
    private final ApplicationProperties applicationProperties;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        if (registrationRepository.findByUsername(registrationRequest.getUsername()).isPresent())
            throw new EntityExistsException("username is taken");
        if (registrationRepository.findByEmail(registrationRequest.getEmail()).isPresent())
            throw new EntityExistsException("email exists");

        AppUser user = convertRequestToEntity(registrationRequest);
        AppUser userFromRepository = saveUser(user);

        return registrationConfirmationService
                .sendConfirmationSequence(userFromRepository.getId(), userFromRepository.getEmail());

    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAppUserRole(AppUserRole.USER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setLocked(false);
        user.setEnabled(false);
        return registrationRepository.save(user);
    }

    @Override
    public String confirmRegistration(String confirmationSequence) {
        Long id = registrationConfirmationService.decodeConfirmationSequence(confirmationSequence);
        if (registrationRepository.findById(id).isEmpty())
            throw new EntityNotFoundException("confirmation link error, try to re-register");

        AppUser user = registrationRepository.findById(id).get();
        checkIfUserValid(user);

        user.setEnabled(true);
        user.setRegistrationDate(LocalDateTime.now());
        registrationRepository.save(user);
        return "successfully registered";
    }

    @Override
    public Boolean checkIfUserValid(AppUser user) {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(applicationProperties.getEXPIRATION_TIME_HOURS());
        if (user.getEnabled()) {
            throw new IllegalStateException("account already enabled");
        } else if (user.getRegistrationDate().isBefore(expirationThreshold)) {
            registrationRepository.deleteById(user.getId());
            throw new IllegalStateException("your link is expired, try again");
        }
        return true;
    }

    @Override
    public AppUser convertRequestToEntity(@Valid RegistrationRequest registrationRequest) {
        Set<ConstraintViolation<RegistrationRequest>> validationExceptions = validator.validate(registrationRequest);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        AppUser user = modelMapper.map(registrationRequest, AppUser.class);
        return user;
    }

    @Scheduled(cron = "@weekly")
    @Override
    public void unconfirmedAccountsCleanup() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(applicationProperties.getEXPIRATION_TIME_HOURS());
        registrationRepository.deleteUnconfirmedAccounts(false, expirationThreshold);
    }

}