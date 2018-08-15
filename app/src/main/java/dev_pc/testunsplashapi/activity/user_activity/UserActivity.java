package dev_pc.testunsplashapi.activity.user_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.CollectionsFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.LikesFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.PhotosFragment;

public class UserActivity extends MvpActivity<IUser.View, UserPresenter> {

    private Context context;
    private Button btnPhotos;
    private Button btnLikes;
    private Button btnCollections;
    private Button btnFollow;
    private CollectionsFragment collectionsFragment;
    private LikesFragment likesFragment;
    private PhotosFragment photosFragment;

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        initResources();
        setBtnPhotos(photosFragment);
        setBtnLikes(likesFragment);
        setBtnCollections(collectionsFragment);

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
    }

    private void setBtnFollow(){
        btnPhotos.setOnClickListener(listener ->
                transaction = getSupportFragmentManager().beginTransaction());
        transaction.add(R.id.user_profile_container, likesFragment).commit();
    }

    private void setBtnPhotos(Fragment fragment){
        btnPhotos.setOnClickListener(listener -> getPresenter().showPhotos(fragment));
    }

    private void setBtnLikes(Fragment fragment){
        btnLikes.setOnClickListener(listener -> getPresenter().showLikes(fragment));
    }

    private void setBtnCollections(Fragment fragment){
        btnCollections.setOnClickListener(listener -> getPresenter().showCollections(fragment));
    }

    @NonNull
    @Override
    public UserPresenter createPresenter() {
        return new UserPresenter(context);
    }
}
