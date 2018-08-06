package dev_pc.testunsplashapi.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import dev_pc.testunsplashapi.service.ApiUnsplash;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageFragment;
import dev_pc.testunsplashapi.recycler_view.image_recycler.MyImageRecyclerViewAdapter;
import dev_pc.testunsplashapi.model.Photo;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPhotoFragment extends AbstractTabFragment implements
        ImageFragment.OnListFragmentInteractionListener{

    private final int LAYOUT = R.layout.fragment_new_foto;
    Photo unsplashModel;
    List<Photo> lists;
    RecyclerView recyclerView;
    OkHttpClient myOkHttpClient;
    String CLIENT_ID = "0309ebb085124bab57ce37c0cb6b9ea1b4f9a3c90208a5739b07f625fe63c87b";

    public static NewPhotoFragment getInstance(Context context) {
        Bundle args = new Bundle();
        NewPhotoFragment fragment = new NewPhotoFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.new_tab_name));
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        publicClient();
        getPublic();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;

    }

    private void getPublic(){
        lists = new ArrayList<>();
        unsplashModel = new Photo();

        recyclerView = view.findViewById(R.id.reclist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MyImageRecyclerViewAdapter adapter = new MyImageRecyclerViewAdapter(lists,this);
        recyclerView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(myOkHttpClient);
        Retrofit retrofit = builder.build();
        ApiUnsplash client = retrofit.create(ApiUnsplash.class);

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
        Toast.makeText(getActivity(), "you touch item of recyclerView?", Toast.LENGTH_LONG).show();
    }

}
