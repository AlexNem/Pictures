package dev_pc.testunsplashapi.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.authentication.Authentication;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.authentication.OkhttpClient;
import dev_pc.testunsplashapi.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageRecyclerViewAdapter;
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

public class NewPhotoFragment extends Fragment{

    private MySharedPreferences mySharedPreferences;
    private ServiceRetrofit serviceRetrofit;
    private Authentication authentication;
    private OkhttpClient myClient;

    private android.view.View view;
    private RecyclerView recyclerView;
    private List<Photo> lists;
    private IListFragment.Presenter listener;
    private final int LAYOUT = R.layout.fragment_new_foto;




    @Override
    public void onStart() {
        super.onStart();
        initRecycler();
        getPublic();
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                          Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        mySharedPreferences = new MySharedPreferences(getContext());
        serviceRetrofit = new ServiceRetrofit();
        authentication = new Authentication(getContext());
        myClient = new OkhttpClient(getContext());
        lists = new ArrayList<>();

        return view;
    }

    private void initRecycler(){
        recyclerView = view.findViewById(R.id.reclist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ImageRecyclerViewAdapter adapter = new ImageRecyclerViewAdapter(lists, listener);
        recyclerView.setAdapter(adapter);
    }

    private void getPublic() {
        OkHttpClient client = myClient.publicClient(mySharedPreferences);
        Retrofit retrofit = serviceRetrofit.getRetrofit(client);
        ApiUnsplash service = retrofit.create(ApiUnsplash.class);
        Observable<List<Photo>> getPublicPhoto = service.getPublicPhotos();
        getPublicPhoto
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPublicPhoto -> {
                            Log.d("TAG", "publicPhotoList " + listPublicPhoto.size());
                            lists.addAll(listPublicPhoto);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                );
    }
    
}
