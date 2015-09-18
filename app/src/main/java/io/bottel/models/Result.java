package io.bottel.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omid on 9/18/2015.
 */
public class Result {
    @SerializedName("result")
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
