package dev_pc.testunsplashapi.activity.start_activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.io.IOException;

import dev_pc.testunsplashapi.authentication.Authentication;
import dev_pc.testunsplashapi.authentication.OkhttpClient;
import dev_pc.testunsplashapi.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.activity.login_activity.IView;
import dev_pc.testunsplashapi.model.AccessToken;
import dev_pc.testunsplashapi.model.User;
import dev_pc.testunsplashapi.util.ConstantApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class StartPresenter extends MvpBasePresenter<IView> {

    private MySharedPreferences mySharedPreferences;
    private Context context;
    private ServiceRetrofit serviceRetrofit;
    private Authentication authentication;
    private OkhttpClient myClient;


    public StartPresenter(Context context) {
        this.context = context;
        mySharedPreferences = new MySharedPreferences(context);
        serviceRetrofit = new ServiceRetrofit();
        authentication = new Authentication(context);
        myClient = new OkhttpClient(context);
    }

    public void getCurrentUser() {
        OkHttpClient client = myClient.tokenClient(mySharedPreferences);
        Retrofit retrofit = serviceRetrofit.getRetrofit(client);
        ApiUnsplash service = retrofit.create(ApiUnsplash.class);
        Observable<User> getUser = service.getUserProfile();
        getUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            Log.d("TAG", "user" + user.getUsername());
                    Toast.makeText(context, " " + user.getUsername(), Toast.LENGTH_LONG).show();
                        }
                );
    }

    public void authorize(){
        authentication.getUri();
    }

    public void getToken(Uri uri) {
        if (uri != null && uri.toString().startsWith(ConstantApi.REDIRECT_URI)) {
            String code;
            code = uri.getQueryParameter("code");
            Log.d("TAG", "code " + code);
           authentication.getToken(code);
        }
    }

}
