package dev_pc.testunsplashapi.authentication;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import dev_pc.testunsplashapi.model.Photo;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRetrofit {

    public ServiceRetrofit() {
    }

    public Retrofit getRetrofit(OkHttpClient client){
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
       return retrofit;

    }
    public Retrofit getTokenRetrofit() {
        Retrofit.Builder builder = new retrofit2.Retrofit.Builder()
                .baseUrl("https://unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit2.Retrofit retrofit = builder.build();
        return retrofit;
    }

}
