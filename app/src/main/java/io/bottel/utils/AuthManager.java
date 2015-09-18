package io.bottel.utils;

/**
 * Created by Omid on 9/17/2015.
 */

import android.content.Context;

import com.google.gson.Gson;

import io.bottel.BottelApp;
import io.bottel.models.User;

/**
 * Created by Omid on 8/26/2015.
 */
public class AuthManager {

    private final static String PREFERENCE_USER = "preference_user";

    public static boolean isLoggedIn(Context context) {
        return getUser(context) != null;
    }

    public static User getUser(Context context) {
        String json = PreferenceUtil.read(context, PREFERENCE_USER, "");
        if (json == null || json.isEmpty())
            return null;
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    public static void login(Context context, User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        PreferenceUtil.save(context, PREFERENCE_USER, json);
    }

    public static void signOut(Context context) {
        // tell server that user is busy from now on..
        PresencePersistent.setCurrentState(context, PresencePersistent.State.BUSY);

        // unregister from call service
        BottelApp.getInstance().getCallService().signOut();

        // remove
        PreferenceUtil.save(context, PREFERENCE_USER, null);
    }
}
