package dev_pc.testunsplashapi.activity.user_activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.R;

public class UserPresenter extends MvpBasePresenter<IUser.View> {

    private Context context;

    public UserPresenter(Context context) {
        this.context = context;
    }

    private void showFragment(Fragment fragment){
//        FragmentManager fragmentManager =
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.user_profile_image, fragment).commit();

    }

}
