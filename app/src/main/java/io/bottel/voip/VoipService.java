package io.bottel.voip;

import android.content.Context;

/**
 * Created by Omid on 9/17/2015.
 */
public abstract class VOIPService {

    private boolean isSetup = false;

    public void init(Context context) {
        isSetup = true;
    }

    public abstract void connect(Context context);

    public void hangUp(Context context) {
//        if (context != null)
//            PresencePersistent.setCurrentState(context, PresencePersistent.State.AVAILABLE);
    }

    public abstract boolean isOnCall();

    public boolean isSetup() {
        return isSetup;
    }

    public abstract void loginAsync(String username, String password);

    public abstract void loginAsync(Context Context, String username, String password);

    public abstract void call(String receipt);

    public abstract boolean isConnected();

    public abstract void hold();

    public abstract void answerCall();

    public abstract void rejectCall();

    public abstract void resume();

    public abstract void signOut();
}
