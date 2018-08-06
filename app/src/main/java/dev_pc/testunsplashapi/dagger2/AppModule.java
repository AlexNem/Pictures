package dev_pc.testunsplashapi.dagger2;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;

@Module(includes = ContextModule.class)
public class AppModule {

   @Provides
   @Singleton
    MySharedPreferences privideSharedPreferences(Context context){
       return new MySharedPreferences(context);
   }

}
