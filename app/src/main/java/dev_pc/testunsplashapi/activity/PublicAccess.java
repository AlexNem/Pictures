package dev_pc.testunsplashapi.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.api.UnsplashApi;
import dev_pc.testunsplashapi.recycler.ImageFragment;
import dev_pc.testunsplashapi.recycler.ImageRecycler;
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


public class PublicAccess extends Fragment implements ImageFragment.OnListFragmentInteractionListener{

    Photo unsplashModel;
    List<Photo> lists;
    RecyclerView recyclerView;
    OkHttpClient myOkHttpClient;
    String CLIENT_ID = "0309ebb085124bab57ce37c0cb6b9ea1b4f9a3c90208a5739b07f625fe63c87b";

    android.view.View view;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pubic);
//        publicClient();
//        getPublic();
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.activity_pubic,container,false);

       publicClient();
       getPublic();
       return view;
    }

    private void getPublic(){
        lists = new ArrayList<>();
        unsplashModel = new Photo();

        recyclerView = view.findViewById(R.id.image_rec_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ImageRecycler adapter = new ImageRecycler(lists,this);
        recyclerView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(myOkHttpClient)
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        UnsplashApi client = retrofit.create(UnsplashApi.class);

        Observable<List<Photo>> getPublic = client.getPublicPhotos();
        getPublic
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
        Toast.makeText(getContext(), "you touch item of recyclerView?", Toast.LENGTH_LONG).show();
    }

//    @Override
////    public void onBackPressed() {
////        new AlertDialog.Builder(getContext())
////                .setTitle("Повернутись?")
////                .setMessage("Ви впевнені? ")
////                .setNegativeButton(android.R.string.no, null)
////                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface arg0, int arg1) {
////
////                        PublicAccess.super.onBackPressed();
////                    }
////                }).create().show();
////    }
}
