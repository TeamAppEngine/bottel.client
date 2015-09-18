package io.bottel.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.bottel.R;
import io.bottel.http.BottelService;
import io.bottel.models.AuthToken;
import io.bottel.models.User;
import io.bottel.services.KeepAliveConnectionService;
import io.bottel.utils.AuthManager;
import io.bottel.utils.EmailValidator;
import io.bottel.utils.GUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Omid on 9/18/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findControls(view);
        configureControls();

    }

    private void configureControls() {
        registerButton.setOnClickListener(this);
    }

    private void findControls(View view) {
        emailEditText = (EditText) view.findViewById(R.id.editText_email);
        passwordEditText = (EditText) view.findViewById(R.id.editText_password);
        registerButton = (Button) view.findViewById(R.id.button_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                final String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // validate input
                if (GUtils.isNullOrEmpty(email) || GUtils.isNullOrEmpty(password) || !EmailValidator.isValidEmailAddress(email)) {
                    Toast.makeText(getActivity(), "Invalid input!", Toast.LENGTH_SHORT).show();
                } else {
                    this.registerButton.setEnabled(false);
                    BottelService.getInstance().registerUser(email, password, new Callback<AuthToken>() {
                        @Override
                        public void success(AuthToken authToken, Response response) {
                            User user = new User();
                            user.setEmail(email);
                            user.setUserID(authToken.getUserId());
                            AuthManager.login(getActivity(), user);
                            getActivity().startService(new Intent(getActivity(), KeepAliveConnectionService.class));
                            changeProfile();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }

    private void changeProfile() {
        // replace with profile fragment
//        Fragment fragment = Fragment.instantiate(getActivity(), ProfileFragment.class.getName());
//        getFragmentManager().beginTransaction()
//                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                .replace(R.id.frame_wrapper, fragment)
//                .commit();

        // remove this fragment
        getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .remove(this)
                .commit();
    }
}
