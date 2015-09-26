package io.bottel.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Omid on 9/18/2015.
 */
public class LocalPin {
    @SerializedName("x")
    private double x;
    @SerializedName("y")
    private double y;
    @SerializedName("description")
    private String description;
    @SerializedName("calls_count")
    private int calls_count;
    @SerializedName("receive_calls_count")
    private int receive_calls_count;
    @SerializedName("countries_to")
    private List<String> countries_to;
    @SerializedName("rate")
    private double rate;
    @SerializedName("minutes_spoken")
    private int minutes_spoken;
//    @SerializedName("languages")
//    private Language languages;
    @SerializedName("id")
    private String id;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalls_count() {
        return calls_count;
    }

    public void setCalls_count(int calls_count) {
        this.calls_count = calls_count;
    }

    public int getReceive_calls_count() {
        return receive_calls_count;
    }

    public void setReceive_calls_count(int receive_calls_count) {
        this.receive_calls_count = receive_calls_count;
    }

    public List<String> getCountries_to() {
        return countries_to;
    }

    public void setCountries_to(List<String> countries_to) {
        this.countries_to = countries_to;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getMinutes_spoken() {
        return minutes_spoken;
    }

    public void setMinutes_spoken(int minutes_spoken) {
        this.minutes_spoken = minutes_spoken;
    }
//
//    public Language getLanguages() {
//        return languages;
//    }
//
//    public void setLanguages(Language languages) {
//        this.languages = languages;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
