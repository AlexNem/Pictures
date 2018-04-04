package dev_pc.testunsplashapi.authentication;

import dev_pc.testunsplashapi.presenters.UnsplashPresenter;
import dev_pc.testunsplashapi.responseModel.AccessToken;
import dev_pc.testunsplashapi.util.ConstantApi;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private Context context;
    private AccessToken accessToken;
    private UnsplashPresenter unsplashPresenter;
    private SharedPreferences sharedPreferences;

    String ACCESS_TOKEN = "access_token";
    String TOKEN_TYPE = "token_type";
    String SCOPE = "scope";
    String CREATED_AT = "created_at";

    public MySharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);

    }

    public AccessToken getMyAccessToken(){
        return new AccessToken(
                sharedPreferences.getString(ACCESS_TOKEN, ""),
                sharedPreferences.getString(TOKEN_TYPE, ""),
                sharedPreferences.getString(SCOPE, ""),
                sharedPreferences.getLong(CREATED_AT, 0)
         );
    }

    public void addToken(AccessToken accessToken){
        SharedPreferences.Editor editor = sharedPreferences.edit()
                .putString(ACCESS_TOKEN, accessToken.getAccessToken())
                .putString(TOKEN_TYPE, accessToken.getTokenType())
                .putString(SCOPE, accessToken.getScope())
                .putLong(CREATED_AT, accessToken.getCreatedAt());
        editor.apply();
    }

    public boolean containToken(){
        return (sharedPreferences.contains(ACCESS_TOKEN));
    }
}
