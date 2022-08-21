package org.aquam.registrationservice.config.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitSender {

    @Value("${emailAPI.address}")
    private String EMAIL_API_BASE_URL;

    public <S> S createService(Class<S> serviceClass) {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EMAIL_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
