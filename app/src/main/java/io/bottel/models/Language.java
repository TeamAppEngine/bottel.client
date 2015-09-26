package io.bottel.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Omid on 9/25/2015.
 */
public class Language {
    @SerializedName("language")
    private List<String> language;

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }
}
