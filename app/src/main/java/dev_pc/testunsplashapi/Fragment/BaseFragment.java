package dev_pc.testunsplashapi.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.authentication.Authentication;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.authentication.OkhttpClient;
import dev_pc.testunsplashapi.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.model.Photo;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageRecyclerViewAdapter;

public abstract class BaseFragment extends MvpFragment<IListFragment.View, BaseFragmentPresenter>{

    protected MySharedPreferences mySharedPreferences;
    protected ServiceRetrofit serviceRetrofit;
    protected Authentication authentication;
    protected OkhttpClient myClient;

    protected android.view.View view;
    protected RecyclerView recyclerView;
    protected List<Photo> lists;
    protected IListFragment.Presenter listener;
    protected ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    public abstract BaseFragmentPresenter createPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initResource();
    }

    private void initResource(){
        mySharedPreferences = new MySharedPreferences(getContext());
        serviceRetrofit = new ServiceRetrofit();
        authentication = new Authentication(getContext());
        myClient = new OkhttpClient(getContext());
    }

//    protected void initRecycler(android.view.View view, int RecID){
//        recyclerView = view.findViewById(RecID);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        ImageRecyclerViewAdapter adapter = new ImageRecyclerViewAdapter(lists, listener);
//        recyclerView.setAdapter(adapter);
//    }

}
