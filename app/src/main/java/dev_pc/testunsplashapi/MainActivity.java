package dev_pc.testunsplashapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.api.UnsplashModel;
import dev_pc.testunsplashapi.image_recycler.ImageFragment;
import dev_pc.testunsplashapi.image_recycler.MyImageRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements ImageFragment.OnListFragmentInteractionListener {

    UnsplashModel unsplashModel;
    List<UnsplashModel> lists;
    RecyclerView recyclerView;
    String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unsplash.com/oauth/authorize"
                + "?client_id=" + ConstantApi.APPLICATION_ID
                + "&redirect_uri=" + ConstantApi.REDIRECT_URI
                + "&response_type=code"
                + "&scope=public+read_user"));
        startActivity(intent);
//        getToken();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TAGSS", "!!!");
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(ConstantApi.REDIRECT_URI)) {

            code = uri.getQueryParameter("code");
            Log.d("TAGSS", "" + code);

        }else  Log.d("TAGS", " pusto" );


    }

    private void getToken(){
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
                Toast.makeText(MainActivity.this, "YES" , Toast.LENGTH_LONG).show();
                Log.d("TAG", "kuku " + response.body().getAccessToken());
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(MainActivity.this, "no", Toast.LENGTH_LONG).show();
            }
        });
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
      Toast.makeText(this, "asd", Toast.LENGTH_LONG).show();
    }
}
