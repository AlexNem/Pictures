package dev_pc.testunsplashapi.activity.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import dev_pc.testunsplashapi.R;
//import dev_pc.testunsplashapi.activity.PublicAccess;
import dev_pc.testunsplashapi.activity.start_activity.StartActivity;
import dev_pc.testunsplashapi.activity.user_activity.UserActivity;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;


public class LoginActivity extends MvpActivity<IView, LoginPresenter>
        implements IView
{
    Button btn_show;
    Button btn_token,btn_user, btn_start, btn_profile;
    Intent startActivity;
    Intent userActivity;

    MySharedPreferences sharedPreferences;
    String tokenSP;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_show = findViewById(R.id.btn_show);
        btn_token = findViewById(R.id.btn_token);
        btn_user = findViewById(R.id.btn_user);
        btn_start = findViewById(R.id.btn_start_activity);
        btn_profile = findViewById(R.id.btn_profile);

        startActivity = new Intent(this, StartActivity.class);
        userActivity = new Intent(this, UserActivity.class);

        sharedPreferences = new MySharedPreferences(getApplicationContext());
        tokenSP = sharedPreferences.getMyAccessToken().getAccessToken();

       pressAuthorize();
       btnUser();
       showSP();
       showStartActivity();
       showProfile();
    }

    void showProfile(){
        btn_profile.setOnClickListener(listener -> startActivity(userActivity));
    }

    void showStartActivity(){
        btn_start.setOnClickListener(listener->{
            startActivity(startActivity);
        });
    }

    void showSP(){
        btn_show.setOnClickListener(listener->{
            if (!tokenSP.equals("")) {
                Toast.makeText(getApplicationContext(), "tokenSP "
                        + sharedPreferences.getMyAccessToken().getAccessToken(), Toast.LENGTH_LONG).show();
            } else Toast.makeText(getApplicationContext(), "Немає токена!\nАвторизуйтесь!",
                    Toast.LENGTH_SHORT).show();
        });
    }

    void btnUser(){
        btn_user.setOnClickListener(listener->{
            if (!tokenSP.equals("")) {
                getPresenter().getCurrentUser();
            }else Toast.makeText(getApplicationContext(), "Немає токена!\nАвторизуйтесь!",
                    Toast.LENGTH_SHORT).show();
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "StartOnResume");
        getPresenter().getToken(getIntent().getData());

    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void pressAuthorize() {
        btn_token.setOnClickListener(listener->{
            getPresenter().authorize();
        });

    }
}
