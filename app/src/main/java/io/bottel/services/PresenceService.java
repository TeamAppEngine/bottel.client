package io.bottel.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import io.bottel.http.BottelService;
import io.bottel.models.User;
import io.bottel.utils.AuthManager;
import io.bottel.utils.CallServiceManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

        Context context = getApplicationContext();
        if (context != null) {
            User currentUser = AuthManager.getUser(context);

            if (currentUser != null) {
                if (CallServiceManager.getCallService() != null && CallServiceManager.getCallService().isConnected()) {
                    Toast.makeText(getApplicationContext(), "ping", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "pong", Toast.LENGTH_SHORT).show();

                    BottelService.getInstance().updatePresence(currentUser.getUserID(), new Callback<List<String>>() {
                        @Override
                        public void success(List<String> strings, Response response) {

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }


//                if (currentUser.canReceiveCall()) {
//                    if (CallServiceManager.getCallService() != null && CallServiceManager.getCallService().isConnected()) {
//                        Toast.makeText(context, PresencePersistent.getCurrentState(context).toString(), Toast.LENGTH_SHORT).show();
//
////                        PresenceWebService service = new PresenceWebService(currentUser.getUserID(), PresencePersistent.getCurrentStateCode(context), new WebserviceReceiverInterface() {
////                            @Override
////                            public void receiveResponse(ResponseConstants command, Object response) {
////                                Toast.makeText(getApplicationContext(), "availability status reported", Toast.LENGTH_SHORT).show();
////                            }
////                        });
//
//                        service.handleWebService(null);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "call service is not ready.", Toast.LENGTH_SHORT).show();
//                        startService(new Intent(context, KeepAliveConnectionService.class));
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "user does not receive calls", Toast.LENGTH_SHORT).show();
//                }

            } else
                Toast.makeText(getApplicationContext(), "user is null", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(getApplicationContext(), "context is null", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }
}