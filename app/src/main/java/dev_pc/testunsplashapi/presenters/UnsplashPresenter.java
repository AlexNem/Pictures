package dev_pc.testunsplashapi.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.io.IOException;

import dev_pc.testunsplashapi.api.UserAuthorizationApi;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.interfaces.IView;
import dev_pc.testunsplashapi.responseModel.AccessToken;
import dev_pc.testunsplashapi.responseModel.User;
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


public class UnsplashPresenter extends MvpBasePresenter<IView> {

    private OkHttpClient myOkHttpClient;
    private MySharedPreferences mySharedPreferences;
    private Context context;


    public UnsplashPresenter(Context context) {
        this.context = context;
        mySharedPreferences = new MySharedPreferences(context);
    }

    public void getCurrentUser() {
        tokenClient(mySharedPreferences);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .client(myOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);
        Observable<User> getUser = client.getUserProfile();
        getUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            Log.d("TAG", "user" + user.getUsername());
                    Toast.makeText(context, " " + user.getUsername(), Toast.LENGTH_LONG).show();
                        }
                );
    }

    public void getUri() {
        if (isViewAttached()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unsplash.com/oauth/authorize"
                    + "?client_id=" + ConstantApi.APPLICATION_ID
                    + "&redirect_uri=" + ConstantApi.REDIRECT_URI
                    + "&response_type=code"
                    + "&scope=public+read_user+write_user+read_photos+write_photos+write_likes+write_followers+read_collections+write_collections"));
            context.startActivity(intent);

        }
    }


    public void getToken(final String code) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);
        Observable<AccessToken> call = client.getAccesToken(
                ConstantApi.APPLICATION_ID,
                ConstantApi.SECRET,
                ConstantApi.REDIRECT_URI,
                code,
                "authorization_code");
        call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accessToken -> {
                    mySharedPreferences.addToken(accessToken);
                    Toast.makeText(context, "" + accessToken.getAccessToken(), Toast.LENGTH_LONG).show();
                });
    }

    public void getCode(Uri uri) {
        if (uri != null && uri.toString().startsWith(ConstantApi.REDIRECT_URI)) {
            String code;
            code = uri.getQueryParameter("code");
            Log.d("TAG", "code " + code);
           getToken(code);
        }
    }

    private void tokenClient(final MySharedPreferences mySharedPreferences){
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
        myOkHttpClient = okHttpBuilder.build();
    }
}
