package dev_pc.testunsplashapi.dagger2;

import android.content.Context;

import dagger.Module;

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }
}
