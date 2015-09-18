package io.bottel.views.mapsActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.bottel.R;

public class UserPageFragment extends Fragment {
    TextView send_a_bottel;
    TextView sent_text_view;
    TextView received_text_view;
    TextView minutes_text_view;
    TextView reached_text_view;
    TextView languages_text_view;

    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_user_page, container, false);

        position = getArguments().getInt("position");

        send_a_bottel = (TextView) rootView.findViewById(R.id.send_a_bottel_text_view_fragment_user_page);
        sent_text_view = (TextView) rootView.findViewById(R.id.sent_text_view_fragment_user_page);
        received_text_view = (TextView) rootView.findViewById(R.id.received_text_view_fragment_user_page);
        minutes_text_view = (TextView) rootView.findViewById(R.id.minutes_text_view_fragment_user_page);
        reached_text_view = (TextView) rootView.findViewById(R.id.reached_text_view_fragment_user_page);
        languages_text_view = (TextView) rootView.findViewById(R.id.languages_text_view_fragment_user_page);

        send_a_bottel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
