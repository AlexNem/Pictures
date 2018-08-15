package dev_pc.testunsplashapi.Fragment;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import dev_pc.testunsplashapi.model.Photo;

public interface IListFragment {

    interface View extends MvpView{
        void initRecycler(View View, int recID);
    }
    interface Presenter{
        void onDowload(Photo photo);

        void onLike(Photo photo);

        void onCollection(Photo photo);
    }
}
