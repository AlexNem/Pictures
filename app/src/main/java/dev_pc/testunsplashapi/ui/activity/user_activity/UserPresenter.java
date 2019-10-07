package dev_pc.testunsplashapi.ui.activity.user_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.service.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.service.authentication.OkhttpClient;
import dev_pc.testunsplashapi.service.authentication.ServiceRetrofit;
import dev_pc.testunsplashapi.model.User;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class UserPresenter extends MvpBasePresenter<IUser.View> {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private ServiceRetrofit serviceRetrofit;
    private OkhttpClient myClient;
    private String userImageUrl;
    SharedPreferences sharedPreferences;

    public UserPresenter(Context context, MySharedPreferences mySharedPreferences,
                         ServiceRetrofit serviceRetrofit, OkhttpClient myClient){
        this.context = context;
        this.mySharedPreferences = new MySharedPreferences(context);
        this.serviceRetrofit = new ServiceRetrofit();
        this.myClient = new OkhttpClient(context);
        sharedPreferences = context.getSharedPreferences("userImageUrl", Context.MODE_PRIVATE);
    }

    public void getCurrentUser(){
        OkHttpClient client = myClient.tokenClient(mySharedPreferences);
        Retrofit retrofit = serviceRetrofit.getRetrofit(client);
        ApiUnsplash service = retrofit.create(ApiUnsplash.class);
        Observable<User> getUser = service.getPublicUserProfile("kaban601");
        getUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Observable<User> publicUser = Observable.just(new User(user.getProfileImage()));
                    Log.d("user Rx ", "\n" + "observable " + publicUser);
                    SharedPreferences.Editor editor = sharedPreferences.edit()
                            .putString("userImageUrl", user.getProfileImage().getLarge());
                    editor.apply();
                    Log.d("user", "\n" + "url " + userImageUrl);
                            Log.d("user",
                                    "user " + user.getUsername() +
                                            user.getPortfolioUrl() + "\n" +
                                            user.getBio() + "\n" +
                                            user.getFirstName() + "\n" +
                                            user.getId() + "\n" +
                                            user.getInstagramUsername() + "\n" +
                                            user.getLastName() + "\n" +
                                            user.getTwitterUsername() + "\n" +
                                            user.getLinks().getLikes() + "\n" +
                                            user.getLinks().getPhotos() + "\n" +
                                            user.getLinks().getHtml() + "\n" +
                                            user.getLinks().getSelf()  + "\n" +
                                            user.getLinks().getPortfolio()
                            );
                        }
                );
    }

    public String getUserImageUrl(){
        String imageUrl = sharedPreferences.getString("userImageUrl", "");
        if(imageUrl.isEmpty()){
            imageUrl = "null";
        }else {
            imageUrl = sharedPreferences.getString("userImageUrl", "");
        }
        return imageUrl;
    }


}

