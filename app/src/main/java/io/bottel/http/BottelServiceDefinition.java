package io.bottel.http;


import java.util.List;

import io.bottel.models.AuthToken;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Omid on 9/17/2015.
 */
public interface BottelServiceDefinition {
    @FormUrlEncoded
    @POST("/users")
    void registerUser(@Field("email") String email, @Field("password") String password, Callback<AuthToken> tokenCallback);

    @POST("/users/{user_id}")
    void saveUser(@Path("user_id") String userId, @Field("full_name") String fullname, @Field("about") String about, @Field("languages") List<String> langs, Callback<List<String>> tokenCallback);


    @POST("/users/{userId}/presence")
    void updatePresence(@Path("userId") String userId, Callback<List<String>> callback);
}
