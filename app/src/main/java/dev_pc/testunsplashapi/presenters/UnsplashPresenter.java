package dev_pc.testunsplashapi.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.api.UserAuthorizationApi;
import dev_pc.testunsplashapi.interfaces.IView;
import dev_pc.testunsplashapi.responseModel.AccessToken;
import dev_pc.testunsplashapi.responseModel.User;
import dev_pc.testunsplashapi.util.ConstantApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UnsplashPresenter extends MvpBasePresenter<IView>{

    public  static String SPToken;
    private SharedPreferences sharedPreferences;
    private Context context;
    private String SHARED_PREFERENCE_TOKEN = "token", token;

    public UnsplashPresenter(Context context) {
        this.context = context;
    }

    public void getCurrentUser(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);
        final Call<User> getUser = client.getUserProfile();
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                response.isSuccessful();
                Toast.makeText(context, "user ",Toast.LENGTH_LONG).show();

                Log.d("TAG", "" + response.body());
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


    public void getToken(String code) {

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
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Toast.makeText(context, "token " + response.body().getAccessToken() , Toast.LENGTH_LONG).show();
                    Log.d("TAG", "token " + response.body().getAccessToken());
                    Log.d("TAG", "token " + response.body().getTokenType());
                    Log.d("TAG", "token " + response.body().getScope());
                    Log.d("TAG", "token " + response.body().getCreatedAt());
                    Log.d("TAG", "" + response.body());
                    token = response.body().getAccessToken();
                    saveTotektoSP();
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
    private void saveTotektoSP(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed =  sharedPreferences.edit()
                .putString(SHARED_PREFERENCE_TOKEN,token);
        ed.commit();
        SPToken = sharedPreferences.getString(SHARED_PREFERENCE_TOKEN,"");
        Log.d("TAG", "SP " + SPToken);
    }
}
