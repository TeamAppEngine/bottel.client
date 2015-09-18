package io.bottel.views;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import io.bottel.R;

public class SignUpActivity extends AppCompatActivity {

    TextView textViewSignUp;
    EditText editTextUsername;
    EditText editTextPassword;

    Typeface typefaceYekan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textViewSignUp = (TextView) findViewById(R.id.activity_sign_up_text_view_register);
        editTextUsername = (EditText) findViewById(R.id.activity_sign_up_edit_text_username);
        editTextPassword = (EditText) findViewById(R.id.activity_sign_up_edit_text_password);

        typefaceYekan = Typeface.createFromAsset(getAssets(), "UBUNTU-TITLE.ttf");

        textViewSignUp.setTypeface(typefaceYekan);
        editTextUsername.setTypeface(typefaceYekan);
        editTextPassword.setTypeface(typefaceYekan);

        //TODO: set sign-up button action
        /*textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> params = new HashMap<>();
                params.put("email", String.valueOf(editTextUsername.getText()));
                params.put("password", String.valueOf(editTextPassword.getText()));
                ConnectToServer.post(
                        SignUpActivity.this,
                        true,
                        "ارتباط با سرور",
                        "ثبت نام",
                        ConnectToServer.baseUri + "api/users",
                        params,
                        false,
                        new ConnectToServer.PostListener() {
                            @Override
                            public void ResponseListener(String response) {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    Session session = new Session(getApplicationContext());
                                    session.insertOrUpdateUserID(result.getString("user_id"));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void ErrorListener(VolleyError error) {
                                //Try to login
                                ConnectToServer.get(
                                        SignUpActivity.this,
                                        true,
                                        "ارتباط با سرور",
                                        "ثبت نام",
                                        ConnectToServer.baseUri + "api/users/login_session?email=" + String.valueOf(editTextUsername.getText()) + "&password=" + String.valueOf(editTextPassword.getText()),
                                        false,
                                        new ConnectToServer.GetListener() {
                                            @Override
                                            public void ResponseListener(String response) {
                                                try {
                                                    JSONObject result = new JSONObject(response);
                                                    Session session = new Session(getApplicationContext());
                                                    session.insertOrUpdateUserID(result.getString("user_id"));
                                                    finish();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void ErrorListener(VolleyError error) {

                                            }
                                        }
                                );
                            }
                        }
                );
            }
        });*/
    }
}
