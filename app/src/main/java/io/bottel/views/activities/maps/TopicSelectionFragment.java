package io.bottel.views.activities.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import io.bottel.R;
import io.bottel.http.BottelService;
import io.bottel.models.Result;
import io.bottel.models.User;
import io.bottel.utils.AuthManager;
import io.bottel.utils.PreferenceUtil;
import io.bottel.views.activities.call.CallActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Omid on 9/18/2015.
 */
public class TopicSelectionFragment extends Fragment {
    private static final String PARTNER_ID = "a.yarveisi@gmail.com";

    Spinner spinner;
    ProgressBar progressBar;
    Button reachOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        reachOutButton = (Button) view.findViewById(R.id.button_reach_out);

        reachOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selection = spinner.getSelectedItemPosition();
                String[] topics = getResources().getStringArray(R.array.topics);
                User user = AuthManager.getUser(getActivity());
                PreferenceUtil.save(getActivity(), "topic", topics[selection]);
                if (user != null)
                    progressBar.setVisibility(View.VISIBLE);
                reachOutButton.setEnabled(false);
                BottelService.getInstance().getCallInfo(user.getUserID(), PARTNER_ID, topics[selection], new Callback<Integer>() {
                    @Override
                    public void success(Integer result, Response response) {

                        reachOutButton.setEnabled(true);
                        progressBar.setVisibility(View.GONE);

                        if ( result == 0 ) {
                            makeACall();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        reachOutButton.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void makeACall() {
        Intent intent = new Intent(getActivity(), CallActivity.class);
        intent.putExtra(CallActivity.BUNDLE_IS_CALLING, true);
        intent.putExtra(CallActivity.BUNDLE_EMAIL, PARTNER_ID);
        startActivity(intent);
    }

}
