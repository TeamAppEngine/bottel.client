package io.bottel.views;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import io.bottel.BottelApp;
import io.bottel.R;
import io.bottel.models.User;
import io.bottel.models.events.OnConnectionStateChanged;
import io.bottel.models.events.OnLoginSuccessful;
import io.bottel.services.KeepAliveConnectionService;
import io.bottel.utils.AuthManager;

/**
 * Created by Omid on 9/17/2015.
 */
public class SplashActivity extends Activity {
    boolean isLeaving = false;
    LinearLayout progressBarContainer;
    TextView connectingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBarContainer = (LinearLayout) findViewById(R.id.container_progressbar);
        connectingTextView = (TextView) findViewById(R.id.textView_connecting);

        isLeaving = false;
        // or else wait for some good news..
    }

    private boolean isBound = false;
    KeepAliveConnectionService service;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // get the service...
            service = ((KeepAliveConnectionService.LocalBinder) iBinder).getService();
            isBound = true;

            // in case our voip connection is established, leave this page..
            if (service.getCallService() != null && service.getCallService().isConnected())
                leaveSplash();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        if (!isBound) {
            Intent intent = new Intent(this, KeepAliveConnectionService.class);
            bindService(intent, connection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void leaveSplash() {
        isLeaving = true;
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }


    public void onEvent(OnLoginSuccessful event) {
        if (!isLeaving)
            leaveSplash();
    }

    public void onEvent(OnConnectionStateChanged change) {
        User user = AuthManager.getUser(SplashActivity.this);

        // sign u
        if (user == null)
            return;

        if (change.getState() == OnConnectionStateChanged.States.FAILED && !BottelApp.getInstance().getCallService().isConnected()) {
            Toast.makeText(SplashActivity.this, "trying again..", Toast.LENGTH_SHORT).show();
        } else if (change.getState() == OnConnectionStateChanged.States.CONNECTED) {
            connectingTextView.setText("logging in..");
        }

    }

}
