package dev_pc.testunsplashapi.activity.user_activity.user_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.activity.user_activity.IUser;
import dev_pc.testunsplashapi.activity.user_activity.UserPresenter;

public class CollectionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_profile, container, false);
        return view;
    }

}
