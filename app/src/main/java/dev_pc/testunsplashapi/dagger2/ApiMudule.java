package dev_pc.testunsplashapi.dagger2;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.util.ConstantApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = AppModule.class)
public class ApiMudule {

    private ApiUnsplash unsplashServise;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private MySharedPreferences sharedPreferences;

    @Provides
    @Singleton
    ApiUnsplash provideServise(){
        initOkHttpClient(sharedPreferences);
        initRetrofit(okHttpClient);
        return unsplashServise;
    }

    private void initRetrofit(OkHttpClient client) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        unsplashServise = retrofit.create(ApiUnsplash.class);
    }

    private void initOkHttpClient(MySharedPreferences sharedPreferences){
        if (sharedPreferences.containToken()){
            okHttpWithToken(sharedPreferences);
        }else okHttpWithoutToken();
    }

    private void okHttpWithToken(MySharedPreferences sharedPreferences) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + sharedPreferences.getMyAccessToken().getAccessToken());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient =  httpClient.build();
    }

    private void okHttpWithoutToken() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization", "Client-ID " + ConstantApi.APPLICATION_ID);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient =  httpClient.build();
    }
}
