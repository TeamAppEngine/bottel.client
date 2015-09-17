package io.bottel.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Omid on 9/17/2015.
 */
public class User {

    @SerializedName("user_id")
    private String userID;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("gender")
    private int gender;

    @SerializedName("country_iso")
    private String countryISO;

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("role")
    private int role;

    public enum Role {
        Callee,
        Receiver,
        CASUAL,
        UNKNOWN
    }

    public User() {
    }

    public User(String userID, String firstName, String lastName, String email, String password, String profileImage, int gender, String countryISO, String birthDate, int role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.gender = gender;
        this.countryISO = countryISO;
        this.birthDate = birthDate;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public Role getRole() {
        switch (getRoleCode()) {
            case 1:
                return Role.Receiver;
            case 2:
                return Role.Callee;
            case 3:
                return Role.CASUAL;
            default:
                return Role.UNKNOWN;
        }
    }

    public static int getRole(Role role) {
        switch (role) {
            case Receiver:
                return 1;
            case Callee:
                return 2;
            case CASUAL:
                return 3;
            default:
                return -1;
        }
    }

    public boolean canReceiveCall() {
        return getRole() == User.Role.Receiver || getRole() == User.Role.Callee;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRoleCode() {
        return this.role;
    }

    public void setRoleCode(int role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
    }

    public String getBirthDateString() {
        return birthDate;
    }

    public Date getBirthDate() {
        return null;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public String getFullname() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
