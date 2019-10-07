package dev_pc.testunsplashapi.ui.fragment;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.model.Photo;

public class BaseFragmentPresenter extends MvpBasePresenter<IListFragment.View> {


    public void onLike(Photo item){
        Log.d("TAG", "like");
    }

    public void onCollection(Photo item){
        Log.d("TAG", "like");
    }

    public void onDownload(Photo item){
        Log.d("TAG", "like");
    }

}
