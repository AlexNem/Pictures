package dev_pc.testunsplashapi.Fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

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
