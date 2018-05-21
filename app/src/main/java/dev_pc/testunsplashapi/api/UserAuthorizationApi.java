package dev_pc.testunsplashapi.api;

import java.util.List;

import dev_pc.testunsplashapi.responseModel.AccessToken;
import dev_pc.testunsplashapi.responseModel.Photo;
import dev_pc.testunsplashapi.responseModel.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface UserAuthorizationApi {

    @POST("oauth/token")
    @FormUrlEncoded
    Observable<AccessToken> getAccesToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code,
            @Field("grant_type") String grantType
    );

    @GET("photos/")
    Observable<List<Photo>> getPublicPhotos();

    @GET("me")
    Observable<User> getUserProfile();

    @GET("photos/curated")
    Observable<List<Photo>> getNewPhoto(
//            @Field("page") int page,
//            @Field("per_page") int per_page,
//            @Field("order_by") String order
    );

}
