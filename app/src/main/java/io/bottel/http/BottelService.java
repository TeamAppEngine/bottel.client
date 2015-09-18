package io.bottel.http;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Omid on 9/17/2015.
 */
public class BottelService {
    static BottelServiceDefinition instance = null;
    private static final String ENDPOINT_URL = "http://178.238.226.60/api";

    public static BottelServiceDefinition getInstance() {
        if (instance == null) {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT_URL)
                    .setClient(new OkClient(new OkHttpClient()));

            RestAdapter adapter = builder.build();

            instance = adapter.create(BottelServiceDefinition.class);
        }

        return instance;
    }
}
