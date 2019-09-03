package dev_pc.testunsplashapi.activity.user_activity.user_fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.Fragment.IListFragment;
import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.authentication.OkhttpClient;
import dev_pc.testunsplashapi.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.model.Photo;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageRecyclerViewAdapter;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class LikesFragment extends Fragment {

    private MySharedPreferences mySharedPreferences;
    private ServiceRetrofit serviceRetrofit;
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


//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("https://api.unsplash.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(myOkHttpClient);
//        Retrofit retrofit = builder.build();
//        ApiUnsplash client = retrofit.create(ApiUnsplash.class);
//
//        Observable<List<Photo>> getPublic = client.getPublicPhotos();
//        getPublic
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(list ->{
//                            Log.d("TAG", "list" + list.size());
//                            lists.addAll(list);
//                            recyclerView.getAdapter().notifyDataSetChanged();
//                        }
//                );


//    private void publicClient(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Request.Builder requestBuilder = request.newBuilder()
//                        .header("Authorization","Client-ID " + CLIENT_ID);
//                Request newRequest = requestBuilder.build();
//                return chain.proceed(newRequest);
//            }
//        });
//        myOkHttpClient = builder.build();
//    }


}
