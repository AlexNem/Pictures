package dev_pc.testunsplashapi.authentication;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkhttpClient {

    private Context context;
    private MySharedPreferences sharedPreferences;

    public OkhttpClient(Context context) {
        this.context = context;
        sharedPreferences = new MySharedPreferences(context);
    }

    public OkHttpClient tokenClient(final MySharedPreferences mySharedPreferences){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder()
                        .header("Authorization", "Bearer " + mySharedPreferences.getMyAccessToken().getAccessToken());
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        });
        OkHttpClient client = okHttpBuilder.build();
        return client;

    }
}
