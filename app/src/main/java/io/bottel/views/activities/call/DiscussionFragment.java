package io.bottel.views.activities.call;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.bottel.BottelApp;
import io.bottel.R;

/**
 * Created by Omid on 9/18/2015.
 */
public class DiscussionFragment extends Fragment {
    TextView hangup;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startTime;
    TextView timeCounter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeCounter = (TextView) view.findViewById(R.id.textView_time);
        hangup = (TextView) view.findViewById(R.id.button_hangup);
        hangup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    leaving = true;
                    BottelApp.getInstance().getCallService().hangUp(getActivity());
                    goToSummary();
                }
            }
        });

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 1000);
    }


    private void goToSummary() {
        Fragment fragment = Fragment.instantiate(getActivity(), SummaryFragment.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.wrapper, fragment, null).commit();
    }

    boolean leaving = false;

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
//                long countDown = 600 * 1000 - updatedTime;
            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs = secs % 60;

            timeCounter.setText("00 : " + String.valueOf(mins) + " : " + String.valueOf(secs));

            if (!leaving)
                customHandler.postDelayed(this, 1000);
        }
    };

}
