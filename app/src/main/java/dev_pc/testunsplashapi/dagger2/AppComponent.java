package dev_pc.testunsplashapi.dagger2;

import javax.inject.Singleton;

import dagger.Component;
import dev_pc.testunsplashapi.activity.login_activity.LoginActivity;
import dev_pc.testunsplashapi.activity.start_activity.StartActivity;
import dev_pc.testunsplashapi.activity.login_activity.LoginPresenter;

@Component(modules = {ApiMudule.class, AppModule.class})
@Singleton
public interface AppComponent {

    void inject(LoginActivity loginActivity);
    void inject(LoginPresenter loginPresenter);
    void inject(StartActivity startActivity);
}
