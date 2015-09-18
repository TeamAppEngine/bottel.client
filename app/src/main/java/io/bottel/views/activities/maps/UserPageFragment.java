package io.bottel.views.activities.maps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.bottel.R;
import io.bottel.models.LocalPin;

public class UserPageFragment extends Fragment {
    TextView send_a_bottel;
    TextView sent_text_view;
    TextView received_text_view;
    TextView minutes_text_view;
    TextView reached_text_view;
    TextView languages_text_view;

    int position;

    public interface BottleSelectionInterface {
        void onClick(int position);
    }

    private BottleSelectionInterface onBottleClicked = null;

    public BottleSelectionInterface getOnBottleClicked() {
        return onBottleClicked;
    }

    public void setOnBottleClicked(BottleSelectionInterface onBottleClicked) {
        this.onBottleClicked = onBottleClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_user_page, container, false);

        position = getArguments().getInt("position");
        LocalPin currentPin = MapsActivity.localPinArrayList.get(position);

        send_a_bottel = (TextView) rootView.findViewById(R.id.send_a_bottel_text_view_fragment_user_page);
        sent_text_view = (TextView) rootView.findViewById(R.id.sent_text_view_fragment_user_page);
        received_text_view = (TextView) rootView.findViewById(R.id.received_text_view_fragment_user_page);
        minutes_text_view = (TextView) rootView.findViewById(R.id.minutes_text_view_fragment_user_page);
        reached_text_view = (TextView) rootView.findViewById(R.id.reached_text_view_fragment_user_page);
        languages_text_view = (TextView) rootView.findViewById(R.id.languages_text_view_fragment_user_page);

        send_a_bottel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                if (onBottleClicked != null)
                    onBottleClicked.onClick(position);
            }
        });

        sent_text_view.setText(currentPin.getCalls_count() + " bottels sent");
        received_text_view.setText(currentPin.getReceive_calls_count() + " bottels received");
        minutes_text_view.setText(currentPin.getMinutes_spoken() + " minutes helping others");
        reached_text_view.setText(currentPin.getCountries_to().size() + " countries reached");
        String languages = "";
        for (String language : currentPin.getLanguages()) {
            language += language + ", ";
        }
        languages_text_view.setText("Languages: " + languages);
        return rootView;
    }
}
