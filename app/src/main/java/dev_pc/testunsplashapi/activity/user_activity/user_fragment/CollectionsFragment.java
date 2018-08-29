package dev_pc.testunsplashapi.activity.user_activity.user_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.activity.user_activity.IUser;
import dev_pc.testunsplashapi.activity.user_activity.UserPresenter;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.authentication.OkhttpClient;
import dev_pc.testunsplashapi.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.model.User;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class CollectionsFragment extends Fragment {
    private MySharedPreferences mySharedPreferences;

    private ServiceRetrofit serviceRetrofit;
    private OkhttpClient myClient;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getPublicUserProfile();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frgment_profile, container, false);

        mySharedPreferences = new MySharedPreferences(getContext());
        serviceRetrofit = new ServiceRetrofit();
        myClient = new OkhttpClient(getContext());
        return view;
    }

    private void getPublicUserProfile(){
            OkHttpClient client = myClient.tokenClient(mySharedPreferences);
            Log.d("TAG", "client " + client.equals(null));
            Retrofit retrofit = serviceRetrofit.getRetrofit(client);
            ApiUnsplash service = retrofit.create(ApiUnsplash.class);
            Observable<User> getUserProfile = service.getPublicUserProfile("kaban601");
            getUserProfile
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            user ->{
                                Log.d("TAG", "resultUserProfile" + user.getUsername().toString());
                            }
                    );
    }

}
