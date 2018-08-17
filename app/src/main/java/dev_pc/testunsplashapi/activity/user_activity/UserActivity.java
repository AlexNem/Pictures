package dev_pc.testunsplashapi.activity.user_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.CollectionsFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.LikesFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.PhotosFragment;

public class UserActivity extends AppCompatActivity
implements View.OnClickListener{

    private Button btnPhotos;
    private Button btnLikes;
    private Button btnCollections;
    private Button btnFollow;
    private CollectionsFragment collectionsFragment;
    private LikesFragment likesFragment;
    private PhotosFragment photosFragment;

    private FragmentTransaction transaction;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        initResources();

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

}
