package io.bottel.views.mapsActivity;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import io.bottel.R;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap;
    private String[] country_iso;
    private String[] country_name;
    private AutoCompleteTextView autoCompleteTextView;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        country_iso = getResources().getStringArray(R.array.country_iso);
        country_name = getResources().getStringArray(R.array.country_name);

        //Get markers
        Intent intent = getIntent();
        try {
            String markersString = intent.getStringExtra("markers");
            JSONArray markersJSONArray = new JSONArray("[]");
            for (int i=0; i<markersJSONArray.length(); i++) {
                //TODO: Read markers data.
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpMapIfNeeded();
        //CardView
        cardView = (CardView) findViewById(R.id.card_view_activity_maps);
        //AutoCompleteTextView set adapter and settings.
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MapsActivity.this,
                android.R.layout.simple_dropdown_item_1line, country_name);
        autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.auto_complete_text_view_activity_maps);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imanager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imanager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                getCountryMarkers(autoCompleteTextView.getText().toString());
            }
        });

        ValueAnimator va = ValueAnimator.ofInt(150, 350);
        va.setDuration(700);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                cardView.getLayoutParams().height = value.intValue();
                cardView.requestLayout();
            }
        });
        va.start();
    }

    private void getCountryMarkers(final String countryName){
        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setTitle("ارتباط با سرور");
        progressDialog.setMessage("دریافت افراد آنلاین کشور مورد نظر");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(countryName,1);
                    if(addressList.size()>0){
                        final LatLng currentLocation = new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.hide();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 3));
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                final Geocoder geocoder = new Geocoder(getApplicationContext());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(addressList.size()>0){
                                        //Toast.makeText(getApplicationContext(), addressList.get(0).getCountryCode(),Toast.LENGTH_SHORT).show();
                                        getCountryMarkers(addressList.get(0).getCountryName());
                                        autoCompleteTextView.setText(addressList.get(0).getCountryName());
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(), "No Country!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
