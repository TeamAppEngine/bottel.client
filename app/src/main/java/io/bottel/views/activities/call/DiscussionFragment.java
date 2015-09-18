package io.bottel.views.activities.call;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.bottel.BottelApp;
import io.bottel.R;

/**
 * Created by Omid on 9/18/2015.
 */
public class DiscussionFragment extends Fragment {
    Button hangup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hangup = (Button) view.findViewById(R.id.button_hangup);
        hangup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    BottelApp.getInstance().getCallService().hangUp(getActivity());
            }
        });
    }
}
