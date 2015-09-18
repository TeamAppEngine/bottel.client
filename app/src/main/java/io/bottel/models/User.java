package io.bottel.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Omid on 9/17/2015.
 */
public class User {

    @SerializedName("user_id")
    private String userID;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("country_iso")
    private String countryISO;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("about")
    private String about;


    public User() {
    }

    public User(String userID, String firstName, String lastName, String email, String password, String profileImage, int gender, String countryISO, String birthDate, int role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.countryISO = countryISO;
    }

    public String getUserID() {
        return userID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
    }


    public Date getBirthDate() {
        return null;
    }


    public String getVoxUsername() {
        String username = this.email + "";
        return username.replace("@", "_");
    }

    public String getVoxAddress() {
        return getVoxUsername() + "@staging-bottel.appengine.voximplant.com";
    }

    public String getVoxPassword() {
        return "1234567";
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
