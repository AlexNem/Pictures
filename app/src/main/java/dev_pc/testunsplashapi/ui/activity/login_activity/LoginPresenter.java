package dev_pc.testunsplashapi.ui.activity.login_activity;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.service.authentication.AuthenticationManager;
import dev_pc.testunsplashapi.service.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.service.authentication.OkhttpClient;
import dev_pc.testunsplashapi.service.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.model.User;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import dev_pc.testunsplashapi.util.ConstantApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class LoginPresenter extends MvpBasePresenter<IView> {

    private MySharedPreferences mySharedPreferences;
    private Context context;
    private ServiceRetrofit serviceRetrofit;
    private AuthenticationManager authenticationManager;
    private OkhttpClient myClient;


    public LoginPresenter(Context context) {
        this.context = context;
        mySharedPreferences = new MySharedPreferences(context);
        serviceRetrofit = new ServiceRetrofit();
        authenticationManager = new AuthenticationManager(context);
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
                            Log.d("TAG",
                                    "user " + user.getUsername() +
                                            user.getPortfolioUrl() +
                                            user.getBio() +
                                            user.getFirstName() +
                                            user.getId() +
                                            user.getInstagramUsername() +
                                            user.getLastName() +
                                            user.getTwitterUsername() +
                                            user.getLinks()
                            );
                    Toast.makeText(context, " " + user.getUsername(), Toast.LENGTH_LONG).show();
                        }
                );
    }

    public void authorize(){
        authenticationManager.getUri();
    }

    public void getToken(Uri uri) {
        if (uri != null && uri.toString().startsWith(ConstantApi.REDIRECT_URI)) {
            String code;
            code = uri.getQueryParameter("code");
            Log.d("TAG", "code " + code);
           authenticationManager.getToken(code);
        }
    }



}
