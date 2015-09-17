package io.bottel.http;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Omid on 9/17/2015.
 */
public class BottelService {
    static BottelServiceDefinition instance = null;
    private static final String ENDPOINT_URL = "https://api.github.com";

    public static BottelServiceDefinition getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .build();
            instance = retrofit.create(BottelServiceDefinition.class);
        }

        return instance;
    }
}
