package dev_pc.testunsplashapi.ui.activity.user_activity.user_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.ui.activity.start_activity.StartActivity;

public class PhotosFragment extends Fragment {

    public interface OnClickFragmentPhotos {
        void photosClick(Intent intent);
    }

    private OnClickFragmentPhotos onClickFragmentPhotos;
    private Button btn_follow;
    private Button btn_test;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickFragmentPhotos) {
            onClickFragmentPhotos = (OnClickFragmentPhotos) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must be implemented");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_profile, container, false);

        btn_test = view.findViewById(R.id.btn_Itest);
        setBtn_test();

        return view;
    }

    public void setBtn_test() {
        btn_test.setOnClickListener(listener -> {
            Intent startIntent = new Intent(getContext(), StartActivity.class);
            onClickFragmentPhotos.photosClick(startIntent);
        });
    }

}
