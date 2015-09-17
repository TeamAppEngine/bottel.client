package io.bottel.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Omid on 9/17/2015.
 */
public class PresenceAlarm {
    private final static int PRESENCE_UPDATE_INTERVAL_MILS = 60000;

    public static boolean init(Context context) {

        // schedule presence updater alarm
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (manager == null)
            return false;


        Intent intent = new Intent(context, PresenceService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, PRESENCE_UPDATE_INTERVAL_MILS, pendingIntent);
        return true;
    }
}
