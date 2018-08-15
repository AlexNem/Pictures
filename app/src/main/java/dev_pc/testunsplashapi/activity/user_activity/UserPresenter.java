package dev_pc.testunsplashapi.activity.user_activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.R;

public class UserPresenter extends MvpBasePresenter<IUser.View>
implements IUser.View{

    private Context context;

    public UserPresenter(Context context) {
        this.context = context;
    }



    private void showFrgment(Fragment fragment){
        UserActivity activity = new UserActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager==null){
            transaction.add(R.id.user_profile_container, fragment).commit();
        }else transaction.replace(R.id.user_profile_container, fragment).commit();

    }

    @Override
    public void showPhotos(Fragment photos) {
        showFrgment(photos);
    }

    @Override
    public void showLikes(Fragment likes) {
        showFrgment(likes);
    }

    @Override
    public void showCollections(Fragment collections) {
        showFrgment(collections);
    }
}
