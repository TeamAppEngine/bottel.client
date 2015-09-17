package io.bottel.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.bottel.BottelApp;
import io.bottel.R;

/**
 * Created by Omid on 9/17/2015.
 */
public class CallActivity extends Activity implements View.OnClickListener {

    public static final String BUNDLE_EMAIL = "bundle_email";

    Button acceptButton;
    Button rejectButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        findControls();
        configureControls();

    }

    private void configureControls() {
        acceptButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);
    }

    private void findControls() {
        acceptButton = (Button) findViewById(R.id.button_accept);
        rejectButton = (Button) findViewById(R.id.button_reject);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_accept:
                BottelApp.getInstance().getCallService().acceptCall();
                break;
            case R.id.button_reject:
                BottelApp.getInstance().getCallService().rejectCall();
                break;
        }
    }
}
