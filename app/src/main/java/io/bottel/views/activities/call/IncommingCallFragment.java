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
public class IncommingCallFragment extends Fragment implements View.OnClickListener {
    Button acceptButton;
    Button rejectButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_incoming_call, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findControls(view);
        configureControls();

    }

    private void configureControls() {
        acceptButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);
    }

    private void findControls(View view) {
        acceptButton = (Button) view.findViewById(R.id.button_accept);
        rejectButton = (Button) view.findViewById(R.id.button_reject);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_accept:
                BottelApp.getInstance().getCallService().acceptCall();
                replaceDiscussion();
                break;
            case R.id.button_reject:
                BottelApp.getInstance().getCallService().rejectCall();
                closeMe();
                break;
        }
    }

    private void closeMe() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    private void replaceDiscussion() {
        Fragment fragment = Fragment.instantiate(getActivity(), DiscussionFragment.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.wrapper, fragment, null).commit();
    }
}
