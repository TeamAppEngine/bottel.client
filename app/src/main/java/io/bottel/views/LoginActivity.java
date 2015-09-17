package io.bottel.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.bottel.R;
import io.bottel.models.User;
import io.bottel.utils.AuthManager;

/**
 * Created by Omid on 9/18/2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    Button omidButton;
    Button neoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findControls();
        omidButton.setOnClickListener(this);
        neoButton.setOnClickListener(this);
    }

    private void findControls() {
        omidButton = (Button) findViewById(R.id.button_omid);
        neoButton = (Button) findViewById(R.id.button_neo);
    }

    @Override
    public void onClick(View view) {
        User user = new User();
        switch (view.getId()) {
            case R.id.button_omid:
                user.setEmail("o.najaee@gmail.com");
                break;
            case R.id.button_neo:
                user.setEmail("neo");
                break;
        }

        AuthManager.login(this, user);
        startActivity(new Intent(this, SplashActivity.class));
    }
}
