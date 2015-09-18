package io.bottel.views.activities.call;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import io.bottel.R;

/**
 * Created by Omid on 9/17/2015.
 */
public class CallActivity extends FragmentActivity {

    public static final String BUNDLE_EMAIL = "bundle_email";
    public static final String BUNDLE_HAS_INCOMING = "bundle_has_incoming";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras().containsKey(BUNDLE_HAS_INCOMING)) {
            Fragment fragment = Fragment.instantiate(this, IncommingCallFragment.class.getName());
            getSupportFragmentManager().beginTransaction().add(R.id.wrapper, fragment, null).commit();
        }
    }

}
