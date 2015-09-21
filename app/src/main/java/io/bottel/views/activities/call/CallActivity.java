package io.bottel.views.activities.call;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import de.greenrobot.event.EventBus;
import io.bottel.BottelApp;
import io.bottel.R;
import io.bottel.models.events.OnCallStatusChanged;

/**
 * Created by Omid on 9/17/2015.
 */
public class CallActivity extends FragmentActivity {

    public static final String BUNDLE_EMAIL = "bundle_email";
    public static final String BUNDLE_HAS_INCOMING = "bundle_has_incoming";
    public static final String BUNDLE_IS_CALLING = "bundle_is_calling";
    String email = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        if (getIntent().getExtras().containsKey(BUNDLE_HAS_INCOMING)) {
            Fragment fragment = Fragment.instantiate(this, IncommingCallFragment.class.getName());
            getSupportFragmentManager().beginTransaction().add(R.id.wrapper, fragment, null).commit();
        } else if (getIntent().getExtras().containsKey(BUNDLE_IS_CALLING)) {
            email = "a.yarveisi_gmail.com";
            BottelApp.getInstance().getCallService().call(email);
            Fragment fragment = Fragment.instantiate(this, CallingFragment.class.getName());
            getSupportFragmentManager().beginTransaction().add(R.id.wrapper, fragment, null).commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.getExtras().containsKey(BUNDLE_HAS_INCOMING)) {
            Fragment fragment = Fragment.instantiate(this, IncommingCallFragment.class.getName());
            getSupportFragmentManager().beginTransaction().add(R.id.wrapper, fragment, null).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(OnCallStatusChanged change) {
        switch (change.getStatus()) {
            case CONNECTED:
                Fragment fragment = Fragment.instantiate(CallActivity.this, DiscussionFragment.class.getName(), null);
                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, fragment, null).commit();
                break;
        }
    }
}
