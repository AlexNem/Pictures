package dev_pc.testunsplashapi.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev_pc.testunsplashapi.R;

public class CuratedFragment extends AbstractTabFragment {

    private final int LAYOUT = R.layout.fragment_curated;

    public static NewPhotoFragment getInstance(Context context){
        Bundle args = new Bundle();
        NewPhotoFragment fragment = new NewPhotoFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.curated_tab_name));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

}
