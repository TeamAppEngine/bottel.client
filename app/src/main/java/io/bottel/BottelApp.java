package io.bottel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import io.bottel.services.KeepAliveConnectionService;
import io.bottel.services.PresenceAlarm;
import io.bottel.utils.CallServiceManager;
import io.bottel.voip.VOIPService;

/**
 * Created by Omid on 9/17/2015.
 */
public class BottelApp extends Application {


    private static BottelApp instance;
//    VOIPService callService;

    public static BottelApp getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, KeepAliveConnectionService.class);
        startService(intent);

        instance = this;

        // start presence report service
        PresenceAlarm.init(this);
    }

    public VOIPService getCallService() {
        return CallServiceManager.getCallService();
    }
}
