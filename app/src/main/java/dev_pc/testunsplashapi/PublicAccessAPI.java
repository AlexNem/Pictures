package dev_pc.testunsplashapi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PublicAccessAPI {
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
