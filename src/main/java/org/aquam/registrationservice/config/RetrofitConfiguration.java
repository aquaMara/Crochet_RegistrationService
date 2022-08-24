package org.aquam.registrationservice.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Component
@RequiredArgsConstructor
public class RetrofitConfiguration {

    private final ApplicationProperties applicationProperties;

    public <S> S createService(Class<S> serviceClass) {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(applicationProperties.getEmailApiBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
