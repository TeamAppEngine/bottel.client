package io.bottel.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.bottel.R;
import io.bottel.http.BottelService;
import io.bottel.models.User;
import io.bottel.utils.AuthManager;
import io.bottel.utils.GUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Omid on 9/18/2015.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    EditText fullNameEditText;
    EditText aboutEditText;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findControls(view);
        configureControls();

    }

    private void configureControls() {
        saveButton.setOnClickListener(this);
    }

    private void findControls(View view) {
        fullNameEditText = (EditText) view.findViewById(R.id.editText_fullname);
        aboutEditText = (EditText) view.findViewById(R.id.editText_about);
        saveButton = (Button) view.findViewById(R.id.button_save);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                this.saveButton.setEnabled(false);

                final String fullname = fullNameEditText.getText().toString();
                String about = aboutEditText.getText().toString();

                // validate input
                if (GUtils.isNullOrEmpty(fullname) || GUtils.isNullOrEmpty(about)) {
                    Toast.makeText(getActivity(), "Invalid input!", Toast.LENGTH_SHORT).show();
                } else {
                    this.saveButton.setEnabled(false);
                    User user = AuthManager.getUser(getActivity());
                    if (user == null)
                        return;

                    BottelService.getInstance().saveUser(user.getUserID(), fullname, about, new ArrayList<String>(), new Callback<List<String>>() {
                        @Override
                        public void success(List<String> list, Response response) {
                            closeMe();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            saveButton.setEnabled(true);
                        }
                    });

                }
                break;
        }
    }

    private void closeMe() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}
