package dev_pc.testunsplashapi;

import java.io.IOException;

import dev_pc.testunsplashapi.api.UserAuthorizationApi;
import dev_pc.testunsplashapi.presenters.UnsplashPresenter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TokenInterceptor {
    private UserAuthorizationApi userAuthorizationApi;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public  UserAuthorizationApi buildRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return userAuthorizationApi = retrofit.create(UserAuthorizationApi.class);
    }



}
