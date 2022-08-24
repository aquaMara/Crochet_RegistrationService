package org.aquam.registrationservice.config.retrofit;

import org.aquam.registrationservice.model.ConfirmationData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailServiceAPI {

    @POST("/api/v1/email")
    Call<String> sendEmail(@Body ConfirmationData confirmationData);
}
