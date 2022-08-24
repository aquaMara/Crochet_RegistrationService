package org.aquam.registrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.registrationservice.config.ApplicationProperties;
import org.aquam.registrationservice.config.RetrofitConfiguration;
import org.aquam.registrationservice.config.retrofit.EmailServiceAPI;
import org.aquam.registrationservice.model.ConfirmationData;
import org.aquam.registrationservice.service.RegistrationConfirmationService;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationConfirmationServiceImpl implements RegistrationConfirmationService {

    private final RetrofitConfiguration retrofitSender;
    private final ApplicationProperties applicationProperties;

    @Override
    public String sendConfirmationSequence(Long id, String email) {

        String confirmationSequence = generateConfirmationSequence(id);
        String confirmationLink =
                applicationProperties.getRegistrationApiBaseUrl()
                        + "registration/confirm/"
                        + confirmationSequence;
        return makeApiCall(new ConfirmationData(email, confirmationLink));
    }

    @Override
    public String makeApiCall(ConfirmationData confirmationData) {

        EmailServiceAPI service = retrofitSender.createService(EmailServiceAPI.class);
        Call<String> syncCall = service.sendEmail(confirmationData);
        String responseBody = "";
        try {
            Response<String> response = syncCall.execute();
            responseBody = response.body();
        } catch (ConnectException e) {
            System.out.println("Could not connect to Email server, check the connection");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return responseBody;
    }

    @Override
    public String generateConfirmationSequence(Long id) {

        String encodedId = Long.toHexString((id ^ applicationProperties.getKEY()));
        return encodedId;
    }

    @Override
    public Long decodeConfirmationSequence(String confirmationSequence) {

        Long decodedId = Long.parseLong(confirmationSequence, 16) ^ applicationProperties.getKEY();
        return decodedId;
    }
}
