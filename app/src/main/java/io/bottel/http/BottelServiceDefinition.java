package io.bottel.http;


import java.util.List;

import io.bottel.models.AuthToken;
import io.bottel.models.LocalPin;
import io.bottel.models.Result;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
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


    @GET("/countries/{country_id}/users/online")
    void getOnlineUsersPerCountry(@Path("country_id") String countryId, Callback<List<LocalPin>> callback);

    @FormUrlEncoded
    @POST("/users/{user_id}/users/{partner_id}/call")
    void getCallInfo(@Path("user_id") String userId, @Path("partner_id") String partnerId, @Field("topic") String topic, Callback<Integer> callback);
}
