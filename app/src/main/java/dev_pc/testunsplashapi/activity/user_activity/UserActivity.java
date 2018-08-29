package dev_pc.testunsplashapi.activity.user_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.CollectionsFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.LikesFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.PhotosFragment;
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

public class UserActivity extends AppCompatActivity implements
        View.OnClickListener,
        PhotosFragment.OnClickFragmentPhotos

{

    private Button btnPhotos;
    private Button btnLikes;
    private Button btnCollections;
    private Button btnFollow;
    private CollectionsFragment collectionsFragment;
    private LikesFragment likesFragment;
    private PhotosFragment photosFragment;

    private FragmentTransaction transaction;
    private MySharedPreferences mySharedPreferences;
    private ServiceRetrofit serviceRetrofit;
    private OkhttpClient myClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initResources();
        setBtnFollow();

    }

    private void initResources(){
        btnCollections = findViewById(R.id.btn_user_collections);
        btnLikes = findViewById(R.id.btn_user_likes);
        btnPhotos = findViewById(R.id.btn_user_photo);
        btnFollow = findViewById(R.id.btn_follow);
        collectionsFragment = new CollectionsFragment();
        likesFragment = new LikesFragment();
        photosFragment = new PhotosFragment();

        mySharedPreferences = new MySharedPreferences(this);
        serviceRetrofit = new ServiceRetrofit();
        myClient = new OkhttpClient(this);

    }

    public void setBtnFollow(){
        btnFollow.setOnClickListener(listener->{
            getPublicUserProfile();
        });
    }

    @Override
    public void onClick(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
            switch (view.getId()){
                case R.id.btn_user_likes:
                    transaction.replace(R.id.user_profile_container, likesFragment);
                    break;
                case R.id.btn_user_collections:
                    transaction.replace(R.id.user_profile_container, collectionsFragment);
                    break;
                case R.id.btn_user_photo:
                    transaction.replace(R.id.user_profile_container, photosFragment);
                    break;
            }transaction.commit();
    }

    private void getPublicUserProfile() {
        if (myClient.equals(null)) {
            Log.d("TAG", "client = null!");
        }
        if (mySharedPreferences.equals(null)) {
            Log.d("TAG", "SP = null!");
        } else {
            OkHttpClient client = myClient.tokenClient(mySharedPreferences);
            Log.d("TAG", "client " + client.equals(null));
            Retrofit retrofit = serviceRetrofit.getRetrofit(client);
            ApiUnsplash service = retrofit.create(ApiUnsplash.class);
            Observable<User> getUserProfile = service.getPublicUserProfile("kaban601");
            getUserProfile
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            user -> {
                                Log.d("TAG", "resultUserProfile" + user.getUsername().toString());
                            }
                    );
        }
    }

    @Override
    public void photosClick(Intent intent) {
        startActivity(intent);
    }
}
