package io.bottel.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.bottel.views.MainActivity;

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
//        Toast.makeText(getApplicationContext(), "call received from callReceiverService", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);

        if (intent != null && intent.getExtras() != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(i);
        }

        return START_NOT_STICKY;
    }
}


