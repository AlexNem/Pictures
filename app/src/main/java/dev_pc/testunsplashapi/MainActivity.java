package dev_pc.testunsplashapi;

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


public class MainActivity extends MvpActivity<IView, IPresenter> implements
        IView,
        ImageFragment.OnListFragmentInteractionListener
{

    UnsplashModel unsplashModel;
    List<UnsplashModel> lists;
    RecyclerView recyclerView;
    Button btn_token,btn_public;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_token = findViewById(R.id.btn_token);
        btn_public = findViewById(R.id.btn_public);

       pressAuthorize();
       btnPublic();

    }

    void btnPublic(){
        btn_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPublic();
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
        return new UnsplashPresenter(getApplicationContext());
    }

    private void getPublic(){
        lists = new ArrayList<>();
        unsplashModel = new UnsplashModel();

        recyclerView = (RecyclerView) findViewById(R.id.reclist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MyImageRecyclerViewAdapter adapter = new MyImageRecyclerViewAdapter(lists,this);
        recyclerView.setAdapter(adapter);
        PublicAccessAPI publicAccessAPI = new PublicAccessAPI();
        publicAccessAPI.buildRetrofit().getPhotos("0309ebb085124bab57ce37c0cb6b9ea1b4f9a3c90208a5739b07f625fe63c87b")
                .enqueue(new Callback<List<UnsplashModel>>() {
                    @Override
                    public void onResponse(Call<List<UnsplashModel>> call, Response<List<UnsplashModel>> response) {
                        if (response.isSuccessful()){
                            Log.d("TAG", Integer.toString(response.body().size()));
                            lists.addAll(response.body());
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UnsplashModel>> call, Throwable t) {
                        Log.d("TAG", t.getMessage());
                    }
                });
    }
    @Override
    public void onListFragmentInteraction(UnsplashModel item) {
      Toast.makeText(this, "you touch item of recyclerView?", Toast.LENGTH_LONG).show();
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
