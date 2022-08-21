package org.aquam.registrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.registrationservice.config.retrofit.EmailServiceAPI;
import org.aquam.registrationservice.config.retrofit.RetrofitSender;
import org.aquam.registrationservice.model.AppUser;
import org.aquam.registrationservice.repository.RegistrationRepository;
import org.aquam.registrationservice.service.RegistrationConfirmationService;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationConfirmationServiceImpl implements RegistrationConfirmationService {

    @Override
    public void sendConfirmationSequence(Long id) {
        String confirmationSequence = generateConfirmationSequence(id);
        System.out.println(confirmationSequence);
        String confirmationLink = "http://localhost:8081/registration/confirm/" + confirmationSequence;
        System.out.println(confirmationLink);
        // todo: send sequence
        EmailServiceAPI service = RetrofitSender.createService(EmailServiceAPI.class);
        Call<String> syncCall = service.registerCustomer(confirmationLink);
        try {
            Response<String> response = syncCall.execute();
            String str = response.body();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // todo: get response status about if email is sent: ok or not ok

    }

    // encode
    @Override
    public String generateConfirmationSequence(Long id) {
        // todo: generate sequence based on id and expiration time
        LocalDateTime registrationAttempt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().minusHours(24);
        String confirmationSequence = expiresAt.toString() + id.toString();
        return confirmationSequence;
    }

    // decode
    // move to registration repository, don't use it here
    // from registration repository
    @Override
    public Boolean confirm(String confirmationSequence) {
        // todo: decode confirmation sequence
        // todo: check if not expired (if expired - delete and register again)
        // todo: check if id exists (if not - error) OR get the id to registration repository
        // or not todo: if everything is ok - return confirmed status true

        /*
        Long id = Long.valueOf(confirmationSequence);

        Optional<AppUser> byId = registrationRepository.findById(id);

        if (registrationRepository.findById(id).isEmpty())
            throw new EntityNotFoundException("no registration attempt");

        AppUser userToConfirm = registrationRepository.findById(id).get();

        // todo: check if confirmationSequence is expired -> should re-register

        userToConfirm.setEnabled(true);
        registrationRepository.save(userToConfirm);
         */
        return true;
    }
}
