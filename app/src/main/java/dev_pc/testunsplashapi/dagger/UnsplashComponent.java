package dev_pc.testunsplashapi.dagger;

import dagger.Component;
import dev_pc.testunsplashapi.presenters.UnsplashPresenter;

@Component(modules = {ApplicationModule.class, UnsplashModule.class })
public interface UnsplashComponent {

    void inject(UnsplashPresenter unsplashPresenter);

}
