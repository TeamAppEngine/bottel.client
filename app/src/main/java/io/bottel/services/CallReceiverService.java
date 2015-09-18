package io.bottel.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.bottel.views.activities.call.CallActivity;

/**
 * Created by Omid on 9/17/2015.
 */
public class CallReceiverService extends Service {
    public static final String BUNDLE_CALLEE_EMAIL = "bundle_callee_email";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i = new Intent(this, CallActivity.class);
        if (intent != null && intent.getExtras() != null) {
            // pass receipt email to the receiver activity
            i.putExtra(CallActivity.BUNDLE_EMAIL, intent.getExtras().getString(BUNDLE_CALLEE_EMAIL));
            i.putExtra(CallActivity.BUNDLE_HAS_INCOMING, true);

            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getApplication().startActivity(i);
        }

        return START_NOT_STICKY;
    }
}


