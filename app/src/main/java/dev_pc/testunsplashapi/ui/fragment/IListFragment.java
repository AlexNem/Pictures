package dev_pc.testunsplashapi.ui.fragment;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import dev_pc.testunsplashapi.model.Photo;

public interface IListFragment {

    interface View extends MvpView{
        void initRecycler( int recID);
    }
    interface Presenter{
        void onDowload(Photo photo);

        void onLike(Photo photo);

        void onCollection(Photo photo);
    }
}
