package dev_pc.testunsplashapi.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import dev_pc.testunsplashapi.service.authentication.AuthenticationManager;
import dev_pc.testunsplashapi.service.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.service.authentication.OkhttpClient;
import dev_pc.testunsplashapi.service.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.model.Photo;
import dev_pc.testunsplashapi.recycler_view.image_recycler.ImageRecyclerViewAdapter;

public abstract class BaseFragment extends MvpFragment<IListFragment.View, BaseFragmentPresenter>{

    protected MySharedPreferences mySharedPreferences;
    protected ServiceRetrofit serviceRetrofit;
    protected AuthenticationManager authenticationManager;
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
        authenticationManager = new AuthenticationManager(getContext());
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
