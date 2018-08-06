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
import dev_pc.testunsplashapi.activity.PublicAccess;
import dev_pc.testunsplashapi.activity.start_activity.StartActivity;
import dev_pc.testunsplashapi.activity.start_activity.StartPresenter;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;


public class LoginActivity extends MvpActivity<IView, StartPresenter>
        implements IView
{
    Button btn_show;
    Button btn_token,btn_public,btn_user,btn_newfoto, btn_start;
    Intent publicIntent,newFotoIntent, startActivity;

    MySharedPreferences sharedPreferences;
    String tokenSP;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_show = findViewById(R.id.btn_show);
        btn_token = findViewById(R.id.btn_token);
        btn_public = findViewById(R.id.btn_public);
        btn_user = findViewById(R.id.btn_user);
        btn_start = findViewById(R.id.btn_start_activity);

        publicIntent = new Intent(this, PublicAccess.class);
        startActivity = new Intent(this, StartActivity.class);
        sharedPreferences = new MySharedPreferences(getApplicationContext());
        tokenSP = sharedPreferences.getMyAccessToken().getAccessToken();

       pressAuthorize();
       btnPublic();
       btnUser();
       showSP();
       showStartActivity();
    }

    void showStartActivity(){
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(startActivity);
            }
        });
    }

    void showSP(){
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tokenSP.equals("")) {
                    Toast.makeText(getApplicationContext(), "tokenSP " + sharedPreferences.getMyAccessToken().getAccessToken(), Toast.LENGTH_LONG).show();
                } else Toast.makeText(getApplicationContext(), "Немає токена!\nАвторизуйтесь!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void btnUser(){
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tokenSP.equals("")) {
                    getPresenter().getCurrentUser();
                }else Toast.makeText(getApplicationContext(), "Немає токена!\nАвторизуйтесь!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void btnPublic(){
        btn_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(publicIntent);
            }
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
    public StartPresenter createPresenter() {
        return new StartPresenter(this);
    }

    @Override
    public void pressAuthorize() {
        btn_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().authorize();
            }
        });

    }
}
