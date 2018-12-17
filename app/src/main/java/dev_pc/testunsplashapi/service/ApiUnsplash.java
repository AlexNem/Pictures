package dev_pc.testunsplashapi.service;

import java.util.List;

import dev_pc.testunsplashapi.model.AccessToken;
import dev_pc.testunsplashapi.model.Photo;
import dev_pc.testunsplashapi.model.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiUnsplash {

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

    @GET("photos/curated")
    Observable<List<Photo>> getCurated(
    );

    @GET("me")
    Observable<User> getUserProfile();

    @GET("users/{username}")
    Observable<User> getPublicUserProfile(
      @Path("username") String username
    );

}
