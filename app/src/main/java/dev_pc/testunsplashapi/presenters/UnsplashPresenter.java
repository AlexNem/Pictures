package dev_pc.testunsplashapi.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class UnsplashPresenter extends MvpBasePresenter<IView> {


    private Long created;
    private String token, type, scope;
    private AccessToken accessToken;
    private OkHttpClient myOkHttpClient;
    private MySharedPreferences mySharedPreferences;
    private Context context;


    public UnsplashPresenter(Context context) {
        this.context = context;
        mySharedPreferences = new MySharedPreferences(context);
    }

    public void getCurrentUser(){
        httpClient(mySharedPreferences);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(myOkHttpClient)
                .build();
        UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);

        final Call<User> getUser = client.getUserProfile();
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Yes! ",Toast.LENGTH_LONG).show();
                    Log.d("TAG","response = " + response.body());
                }
                else {
                    Toast.makeText(context, "user ",Toast.LENGTH_LONG).show();

                    Log.d("TAG", "Current body" + response.body());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(context, "Looser!!!", Toast.LENGTH_LONG).show();
            }
        });
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
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);

            Call<AccessToken> accessTokenCall = client.getAccesToken(
                    ConstantApi.APPLICATION_ID,
                    ConstantApi.SECRET,
                    ConstantApi.REDIRECT_URI,
                    code,
                    "authorization_code"
            );
            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(@NonNull Call<AccessToken>  call, @NonNull Response<AccessToken> response) {
                    try {
                        Toast.makeText(context, "token " + response.body().getAccessToken() , Toast.LENGTH_SHORT).show();
                        token = response.body().getAccessToken();
                        type = response.body().getTokenType();
                        scope = response.body().getScope();
                        created = response.body().getCreatedAt();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("TAG", " " + e);
                    }

                    accessToken = new AccessToken(token, type, scope, created);
                    mySharedPreferences.addToken(accessToken);
                    httpClient(mySharedPreferences);
                    Log.d("TAG", "" + myOkHttpClient);

                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                }
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

    private OkHttpClient httpClient(final MySharedPreferences mySharedPreferences){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder()
                        .header("Authorization:", mySharedPreferences.getMyAccessToken().getAccessToken());
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        });
       return myOkHttpClient = okHttpBuilder.build();

    }
}
