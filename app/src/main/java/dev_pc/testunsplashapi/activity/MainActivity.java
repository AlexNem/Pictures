package dev_pc.testunsplashapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import dev_pc.testunsplashapi.Fragment.NewPhotoFragment;
import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.interfaces.IView;
import dev_pc.testunsplashapi.presenters.UnsplashPresenter;


public class MainActivity extends MvpActivity<IView, UnsplashPresenter>
        implements IView
{
    Button btn_show;
    Button btn_token,btn_public,btn_user,btn_fragment, btn_start;
    Intent publicIntent, startActivity;

    MySharedPreferences sharedPreferences;
    String tokenSP;
    NewPhotoFragment photoFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_show = findViewById(R.id.btn_show);
        btn_token = findViewById(R.id.btn_token);
        btn_public = findViewById(R.id.btn_public);
        btn_user = findViewById(R.id.btn_user);
        btn_start = findViewById(R.id.btn_start_activity);
        btn_fragment = findViewById(R.id.btn_photo_fragment);

        publicIntent = new Intent(this, PublicAccess.class);
        startActivity = new Intent(this, StartActivity.class);
        sharedPreferences = new MySharedPreferences(getApplicationContext());
        tokenSP = sharedPreferences.getMyAccessToken().getAccessToken();

        photoFragment = new NewPhotoFragment();

       pressAuthorize();
       btnPublic();
       btnUser();
       showSP();
       showStartActivity();
//       showFragment();
    }

    private void showFragment() {
        btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.login_container, photoFragment);
                transaction.commit();
            }
        });
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
        getPresenter().getCode(getIntent().getData());

    }

    @NonNull
    @Override
    public UnsplashPresenter createPresenter() {
        return new UnsplashPresenter(this);
    }

    @Override
    public void pressAuthorize() {
        btn_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getUri();
            }
        });

    }
}
