package org.aquam.registrationservice.config.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSender {

    private static final String API_BASE_URL = "http://localhost:8082/";

    public Retrofit createRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
