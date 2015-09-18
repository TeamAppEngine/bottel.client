package io.bottel.views.activities.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import io.bottel.R;
import io.bottel.http.BottelService;
import io.bottel.models.Result;
import io.bottel.models.User;
import io.bottel.utils.AuthManager;
import io.bottel.views.activities.call.CallActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Omid on 9/18/2015.
 */
public class TopicSelectionFragment extends Fragment {
    private static final String PARTNER_ID = "k1_gmail.com";

    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        view.findViewById(R.id.button_reach_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selection = spinner.getSelectedItemPosition();
                User user = AuthManager.getUser(getActivity());
                if (user != null)
                    BottelService.getInstance().getCallInfo(PARTNER_ID, user.getUserID(), new Callback<Result>() {
                        @Override
                        public void success(Result result, Response response) {
                            makeACall();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
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
