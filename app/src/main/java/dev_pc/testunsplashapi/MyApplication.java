package dev_pc.testunsplashapi;

import android.app.Application;

import dev_pc.testunsplashapi.dagger.ApplicationModule;
import dev_pc.testunsplashapi.dagger.DaggerUnsplashComponent;
import dev_pc.testunsplashapi.dagger.UnsplashComponent;
import dev_pc.testunsplashapi.dagger.UnsplashModule;

public class MyApplication extends Application {

    private static UnsplashComponent component;

    public static UnsplashComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected UnsplashComponent buildComponent(){
        return DaggerUnsplashComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .unsplashModule(new UnsplashModule())
                .build();
    }
}
