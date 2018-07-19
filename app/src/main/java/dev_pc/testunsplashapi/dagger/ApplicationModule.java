package dev_pc.testunsplashapi.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    Context context(){
        return context;
    }

    @Provides
    MySharedPreferences sharedPreferences(){
        return new MySharedPreferences(context);
    }

}
