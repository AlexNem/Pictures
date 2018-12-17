package dev_pc.testunsplashapi.authentication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import dev_pc.testunsplashapi.model.AccessToken;
import dev_pc.testunsplashapi.service.ApiUnsplash;
import dev_pc.testunsplashapi.util.ConstantApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AuthenticationManager {

    private Context context;
    private MySharedPreferences sharedPreferences;
    private ServiceRetrofit serviceRetrofit;

    public AuthenticationManager(Context context) {
        this.context = context;
        serviceRetrofit = new ServiceRetrofit();
        sharedPreferences = new MySharedPreferences(context);
    }

    public void getToken(final String code) {
        Retrofit retrofit = serviceRetrofit.getTokenRetrofit();
        ApiUnsplash client = retrofit.create(ApiUnsplash.class);
        Observable<AccessToken> call = client.getAccesToken(
                ConstantApi.APPLICATION_ID,
                ConstantApi.SECRET,
                ConstantApi.REDIRECT_URI,
                code,
                "authorization_code");
        call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accessToken -> {
                    sharedPreferences.addToken(accessToken);
                    Toast.makeText(context, "" + accessToken.getAccessToken(), Toast.LENGTH_LONG).show();
                });
    }

    public void getUri() {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unsplash.com/oauth/authorize"
                    + "?client_id=" + ConstantApi.APPLICATION_ID
                    + "&redirect_uri=" + ConstantApi.REDIRECT_URI
                    + "&response_type=code"
                    + "&scope=public+read_user+write_user+read_photos+write_photos+write_likes+write_followers+read_collections+write_collections"));
            context.startActivity(intent);
    }
}
