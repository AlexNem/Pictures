package dev_pc.testunsplashapi.dagger;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dev_pc.testunsplashapi.api.UnsplashApi;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.util.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ApplicationModule.class)
public class UnsplashModule {

    MySharedPreferences sharedPreferences;
    Context context;

    public UnsplashModule(){}

    private OkHttpClient publicClient;
    private OkHttpClient userClient;

    @Provides
    UnsplashApi unsplashApi(){
        initClient();
        Retrofit retrofit;
        if (sharedPreferences.containToken()){
            retrofit = retrofit(userClient);
        }else {
            retrofit = retrofit(publicClient);
        }
        return retrofit.create(UnsplashApi.class);
    }

    private void initClient(){
        if (sharedPreferences.containToken()){
            userOkHttpClient(sharedPreferences);
        }else publicOkHttpClient();
    }

    private Retrofit retrofit (OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void publicOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder()
                                .header("Authorization","Client-ID " + Constants.APPLICATION_ID);
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                });
        publicClient = builder.build();
    }

   private void userOkHttpClient(MySharedPreferences mySharedPreferences) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder()
                                .header("Authorization", "Client-ID " + mySharedPreferences.getMyAccessToken());
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                });
        userClient = builder.build();
    }
}
