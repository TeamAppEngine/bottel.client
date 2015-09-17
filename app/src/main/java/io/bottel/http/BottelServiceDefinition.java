package io.bottel.http;


import io.bottel.models.AuthToken;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Omid on 9/17/2015.
 */
public interface BottelServiceDefinition {
    @FormUrlEncoded
    @POST("/users")
    void registerUser(@Field("email") String email, @Field("password") String password, Callback<AuthToken> tokenCallback);

}
