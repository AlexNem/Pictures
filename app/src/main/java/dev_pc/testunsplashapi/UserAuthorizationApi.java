package dev_pc.testunsplashapi;

import java.util.List;

import dev_pc.testunsplashapi.api.UnsplashModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;



interface UserAuthorizationApi {
   // https://unsplash.com/oauth/authorize?client_id=0309ebb085124bab57ce37c0cb6b9ea1b4f9a3c90208a5739b07f625fe63c87b
    // &redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&response_type=code&scope=public

//    @POST("oauth/authorize/")
//    Call<List<AuthorizeModel>>postUserInf(@Query("client_id") String client_id,
//                                         @Query("redirect_uri") String redirect_uri,
//                                         @Query("response_type") String response_type,
//                                         @Query("scope") String scope);
//
//    @POST("https://unsplash.com/oauth/token")
//    Call<List<OuthTokenModel>>postToken(@Query("client_id") String client_id,
//                           @Query("client_secret") String client_secret,
//                           @Query("redirect_uri") String redirect_uri,
//                           @Query("code") String code,
//                           @Query("grant_type") String grant_type);
    @POST("oauth/token")
    @FormUrlEncoded
    Call<AccessToken> getAccesToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code,
            @Field("grant_type") String grantType
    );

    @GET("photos/")
    Call<List<UnsplashModel>> getPhotos(@Query("client_id") String client_id);
}
