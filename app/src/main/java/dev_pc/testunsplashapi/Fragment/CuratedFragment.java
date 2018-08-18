package dev_pc.testunsplashapi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageRecyclerViewAdapter;

public class CuratedFragment extends BaseFragment {


    private final int LAYOUT = R.layout.fragment_curated;

    @Override
    public BaseFragmentPresenter createPresenter() {
        return new BaseFragmentPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

}
