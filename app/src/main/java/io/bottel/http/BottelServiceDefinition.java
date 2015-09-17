package io.bottel.http;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Omid on 9/17/2015.
 */
public interface BottelServiceDefinition {
    @GET("id")
    void id(String username, Callback<String> message);

}
