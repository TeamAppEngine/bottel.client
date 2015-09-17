package io.bottel.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omid on 9/18/2015.
 */
public class AuthToken {
    @SerializedName("user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
