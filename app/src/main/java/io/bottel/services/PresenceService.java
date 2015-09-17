package io.bottel.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import io.bottel.utils.CallServiceManager;

/**
 * Created by Omid on 9/17/2015.
 */
public class PresenceService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

//        Context context = getApplicationContext();
//        if (context != null) {
////            User currentUser = AuthManager.getUser(context);
//
////            if (currentUser != null) {
////
////                if (currentUser.canReceiveCall()) {
////                    if (CallServiceManager.getCallService() != null && CallServiceManager.getCallService().isConnected()) {
////                        Toast.makeText(context, PresencePersistent.getCurrentState(context).toString(), Toast.LENGTH_SHORT).show();
//
////                        PresenceWebService service = new PresenceWebService(currentUser.getUserID(), PresencePersistent.getCurrentStateCode(context), new WebserviceReceiverInterface() {
////                            @Override
////                            public void receiveResponse(ResponseConstants command, Object response) {
////                                Toast.makeText(getApplicationContext(), "availability status reported", Toast.LENGTH_SHORT).show();
////                            }
////                        });
////
////                        service.handleWebService(null);
////                    } else {
////                        Toast.makeText(getApplicationContext(), "call service is not ready.", Toast.LENGTH_SHORT).show();
////                        startService(new Intent(context, KeepAliveConnectionService.class));
////                    }
////                } else {
////                    Toast.makeText(getApplicationContext(), "user does not receive calls", Toast.LENGTH_SHORT).show();
////                }
//
//            } else
//                Toast.makeText(getApplicationContext(), "user is null", Toast.LENGTH_SHORT).show();
//
//        }
////    else
////            Toast.makeText(getApplicationContext(), "context is null", Toast.LENGTH_SHORT).show();
//
//        return START_NOT_STICKY;
//    }
}

