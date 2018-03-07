package dev_pc.testunsplashapi.interfaces;

import android.net.Uri;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;



public interface IPresenter extends MvpPresenter<IView> {

    void getCode(Uri uri);
    void getUri();
    void getToken(String code);

}
