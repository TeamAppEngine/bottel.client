package io.bottel.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import io.bottel.BottelApp;
import io.bottel.R;
import io.bottel.utils.AuthManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button callOmid;
    Button callNeo;
    Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findControls();
        configureControls();
    }

    private void configureControls() {
        this.callOmid.setOnClickListener(this);
        this.callNeo.setOnClickListener(this);
        this.signout.setOnClickListener(this);
    }

    private void findControls() {
        callOmid = (Button) findViewById(R.id.button_call_omid);
        callNeo = (Button) findViewById(R.id.button_call_neo);
        signout = (Button) findViewById(R.id.button_sign_out);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_call_omid:
                BottelApp.getInstance().getCallService().call("o.najaee_gmail.com");
                break;
            case R.id.button_call_neo:
                BottelApp.getInstance().getCallService().call("neo");
                break;
            case R.id.button_sign_out:
                AuthManager.signOut(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up callOmid, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
