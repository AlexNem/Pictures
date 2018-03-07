package dev_pc.testunsplashapi.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import dev_pc.testunsplashapi.AccessToken;
import dev_pc.testunsplashapi.ConstantApi;
import dev_pc.testunsplashapi.UserAuthorizationApi;
import dev_pc.testunsplashapi.interfaces.IPresenter;
import dev_pc.testunsplashapi.interfaces.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UnsplashPresenter extends MvpBasePresenter<IView>
implements IPresenter {

    Context context;

    public UnsplashPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getUri() {
        if (isViewAttached()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unsplash.com/oauth/authorize"
                    + "?client_id=" + ConstantApi.APPLICATION_ID
                    + "&redirect_uri=" + ConstantApi.REDIRECT_URI
                    + "&response_type=code"
                    + "&scope=public+read_user"));
            context.startActivity(intent);
        }
    }

    @Override
    public void getToken(String code) {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://unsplash.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            UserAuthorizationApi client = retrofit.create(UserAuthorizationApi.class);
            Call<AccessToken> accessTokenCall = client.getAccesToken(
                    ConstantApi.APPLICATION_ID,
                    ConstantApi.SECRET,
                    ConstantApi.REDIRECT_URI,
                    code,
                    "authorization_code"
            );
            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Toast.makeText(context, "token " + response.body().getAccessToken() , Toast.LENGTH_LONG).show();
                    Log.d("TAG", "token " + response.body().getAccessToken());
                    Log.d("TAG", "token " + response.body().getTokenType());
                    Log.d("TAG", "token " + response.body().getScope());
                    Log.d("TAG", "token " + response.body().getCreatedAt());
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public void getCode(Uri uri) {
        if (uri != null && uri.toString().startsWith(ConstantApi.REDIRECT_URI)) {
            String code;
            code = uri.getQueryParameter("code");
            Log.d("TAG", "code " + code);
           getToken(code);
        }
    }
}
