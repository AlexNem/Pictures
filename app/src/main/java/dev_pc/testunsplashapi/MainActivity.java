package dev_pc.testunsplashapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.api.UnsplashModel;
import dev_pc.testunsplashapi.image_recycler.ImageFragment;
import dev_pc.testunsplashapi.image_recycler.MyImageRecyclerViewAdapter;
import dev_pc.testunsplashapi.interfaces.IPresenter;
import dev_pc.testunsplashapi.interfaces.IView;
import dev_pc.testunsplashapi.presenters.UnsplashPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends MvpActivity<IView, IPresenter>
        implements IView
{
    Button btn_token,btn_public;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_token = findViewById(R.id.btn_token);
        btn_public = findViewById(R.id.btn_public);

        intent = new Intent(this, PublicAccess.class);

       pressAuthorize();
       btnPublic();

    }

    void btnPublic(){
        btn_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(intent);
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
    public IPresenter createPresenter() {
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
