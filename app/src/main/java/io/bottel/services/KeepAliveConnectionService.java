package io.bottel.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.bottel.models.User;
import io.bottel.utils.AuthManager;
import io.bottel.utils.CallServiceManager;
import io.bottel.voip.VOIPService;
import io.bottel.voip.VoxClient;

/**
 * Created by Omid on 9/17/2015.
 */
public class KeepAliveConnectionService extends Service {
    public VOIPService callService = null;

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public KeepAliveConnectionService getService() {
            // Return this instance of LocalService so clients can call public methods
            return KeepAliveConnectionService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (this.callService == null) {
            this.callService = new VoxClient();
            this.callService.init(getApplicationContext());
            // set global call service reference
            CallServiceManager.setCallService(callService);
        }

        User user = AuthManager.getUser(getApplicationContext());
        if (user == null)
            return;

        this.callService.loginAsync(getApplicationContext(), user.getVoxAddress(), user.getVoxPassword());
//        Toast.makeText(getApplicationContext(), "call service started.", Toast.LENGTH_SHORT).show();
    }

    public VOIPService getCallService() {
        return callService;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        User user = AuthManager.getUser(getApplicationContext());
        if (user != null) {
            this.callService.loginAsync(getApplicationContext(), user.getVoxAddress(), user.getVoxPassword());
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
//        Toast.makeText(getApplicationContext(), "call service destroyed.", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
