package dev_pc.testunsplashapi.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.api.UserAuthorizationApi;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageFragment;
import dev_pc.testunsplashapi.recycler_view.image_recycler.MyImageRecyclerViewAdapter;
import dev_pc.testunsplashapi.responseModel.Photo;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewPhoto extends AppCompatActivity implements ImageFragment.OnListFragmentInteractionListener{

    Photo unsplashModel;
    List<Photo> lists;
    RecyclerView recyclerView;
    OkHttpClient myOkHttpClient;
    String CLIENT_ID = "0309ebb085124bab57ce37c0cb6b9ea1b4f9a3c90208a5739b07f625fe63c87b";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_foto_activity);
        publicClient();
        getNewPhoto();
    }

    private void getNewPhoto(){
        lists = new ArrayList<>();
        unsplashModel = new Photo();

        recyclerView = findViewById(R.id.new_photo_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MyImageRecyclerViewAdapter adapter = new MyImageRecyclerViewAdapter(lists,this);
        recyclerView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(myOkHttpClient);
        Retrofit retrofit = builder.build();
        UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);

        Observable<List<Photo>> getNewPhoto = client.getNewPhoto();
        getNewPhoto
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->{
                            Log.d("TAG", "list" + list.size());
                            lists.addAll(list);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                );
    }

    private void publicClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder()
                        .header("Authorization","Client-ID " + CLIENT_ID);
                Request newRequest = requestBuilder.build();
                return chain.proceed(newRequest);
            }
        });
        myOkHttpClient = builder.build();
    }

    @Override
    public void onListFragmentInteraction(Photo item) {
        Toast.makeText(this, "you touch item of recyclerView?", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Повернутись?")
                .setMessage("Подумай добре! :)")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        NewPhoto.super.onBackPressed();
                    }
                }).create().show();
    }
}
