package dev_pc.testunsplashapi;

import android.app.Application;

import dev_pc.testunsplashapi.dagger2.ApiMudule;
import dev_pc.testunsplashapi.dagger2.AppComponent;
import dev_pc.testunsplashapi.dagger2.AppModule;
import dev_pc.testunsplashapi.dagger2.DaggerAppComponent;

public class MyApplication extends Application {

    public static AppComponent component;

    public static AppComponent getComponent(){
        return component;
}

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .apiMudule(new ApiMudule())
                .appModule(new AppModule())
                .build();
    }
}

