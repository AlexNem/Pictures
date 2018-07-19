package dev_pc.testunsplashapi;

import dev_pc.testunsplashapi.api.UnsplashApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TokenInterceptor {
    private UnsplashApi unsplashApi;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public UnsplashApi buildRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return unsplashApi = retrofit.create(UnsplashApi.class);
    }



}
