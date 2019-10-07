package dev_pc.testunsplashapi.service.authentication;

import dev_pc.testunsplashapi.ui.activity.login_activity.LoginPresenter;
import dev_pc.testunsplashapi.model.AccessToken;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private Context context;
    private AccessToken accessToken;
    private LoginPresenter loginPresenter;
    private SharedPreferences sharedPreferences;

   private String ACCESS_TOKEN = "access_token";
   private String TOKEN_TYPE = "token_type";
   private String SCOPE = "scope";
   private String CREATED_AT = "created_at";

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
