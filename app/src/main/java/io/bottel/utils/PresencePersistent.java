package io.bottel.utils;

/**
 * Created by Omid on 9/17/2015.
 */

import android.content.Context;
import android.content.Intent;

import io.bottel.services.PresenceService;
public class PresencePersistent {

    private final static String PREFERENCE_PRESENCE = "preference_presence";

    public enum State {
        ON_CALL,
        BUSY,
        AVAILABLE,
        UNKNOWN
    }

    public static int getStateCode(State state) {
        switch (state) {
            case AVAILABLE:
                return 1;
            case BUSY:
                return 3;
            case ON_CALL:
                return 6;
            default:
                // unknown
                return -1;
        }
    }

    private static State getStateFromCode(int code) {
        switch (code) {
            case 1:
                return State.AVAILABLE;
            case 3:
                return State.BUSY;
            case 6:
                return State.ON_CALL;
            default:
                return State.UNKNOWN;
        }
    }


    public static State getCurrentState(Context context) {
        return getStateFromCode(getCurrentStateCode(context));
    }

    public static int getCurrentStateCode(Context context) {
        return PreferenceUtil.read(context, PREFERENCE_PRESENCE, -1);
    }

    public static void setCurrentState(Context context, State newState) {
        State previous = getCurrentState(context);

        PreferenceUtil.save(context, PREFERENCE_PRESENCE, getStateCode(newState));

        if (previous != newState)
            notifyServerAsync(context);
    }

    public static void notifyServerAsync(Context context) {
        Intent intent = new Intent(context, PresenceService.class);
        context.startService(intent);
    }
}
