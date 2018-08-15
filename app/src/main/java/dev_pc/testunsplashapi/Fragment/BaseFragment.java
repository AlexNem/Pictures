package dev_pc.testunsplashapi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.model.Photo;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageRecyclerViewAdapter;

public abstract class BaseFragment extends MvpFragment<IListFragment.View, BaseFragmentPresenter>
implements IListFragment.View {

    protected android.view.View view;
    protected RecyclerView recyclerView;
    protected ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    protected IListFragment.Presenter listener;
    protected List<Photo> lists;
    public abstract BaseFragmentPresenter createPresenter();


    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_list, container, false);
//        initRecycler(view, R.id.reclist);
        return view;
    }


//    public void initRecycler(android.view.View view, int recID){
//        recyclerView = view.findViewById(recID);
//        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(new ArrayList<>(), getPresenter());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//    }

}
