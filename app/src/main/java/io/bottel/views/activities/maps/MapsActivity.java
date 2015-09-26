package io.bottel.views.activities.maps;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.bottel.R;
import io.bottel.http.BottelService;
import io.bottel.models.LocalPin;
import io.bottel.models.events.OnLoginSuccessful;
import io.bottel.models.events.OnUserLoggedIn;
import io.bottel.services.KeepAliveConnectionService;
import io.bottel.utils.AuthManager;
import io.bottel.views.fragments.LoginFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    String[] country_iso;
    String[] country_name;
    private AutoCompleteTextView autoCompleteTextView;

    CardView cardView;
    private List<LocalPin> localPins = null;
    private LinearLayout linearLayout;

    static int NUM_PAGES;
    ViewPager mPager;
    PagerAdapter mPagerAdapter;

    public static List<LocalPin> localPinArrayList = new ArrayList<>();
    public List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.view_pager_activity_main);
        mPagerAdapter = new UserPagerAdapter(getSupportFragmentManager());


        country_iso = getResources().getStringArray(R.array.country_iso);
        country_name = getResources().getStringArray(R.array.country_name);

        setUpMapIfNeeded();
        //CardView
        cardView = (CardView) findViewById(R.id.card_view_activity_maps);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_activity_maps);
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try{
                    mPager.setCurrentItem(markers.indexOf(marker));
                }catch(Exception e){

                }
                return false;
            }
        });
    }

    private void getCountryMarkers(final String countryName) {

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
                    List<Address> addressList = geocoder.getFromLocationName(countryName, 1);
                    if (addressList.size() > 0) {

                        BottelService.getInstance().getOnlineUsersPerCountry(addressList.get(0).getCountryCode(), new Callback<List<LocalPin>>() {
                            @Override
                            public void success(List<LocalPin> localPins, Response response) {
                                localPinArrayList = localPins;
                                NUM_PAGES = localPins.size();
                                mMap.clear();
                                markers.clear();
                                for (LocalPin localPin : localPins) {
                                    markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(localPin.getX(), localPin.getY()))));
                                }
                                mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    }

                                    @Override
                                    public void onPageSelected(int position) {

                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(localPinArrayList.get(position).getX() - 3, localPinArrayList.get(position).getY()), 6));
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                                mPager.setAdapter(mPagerAdapter);
                                progressDialog.dismiss();
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

                        final LatLng currentLocation = new LatLng(addressList.get(0).getLatitude()-3, addressList.get(0).getLongitude());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                progressDialog.hide();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 6));
                                ValueAnimator va = ValueAnimator.ofInt(0, dpToPx(250));

                                va.setDuration(1000);
                                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        Integer value = (Integer) animation.getAnimatedValue();
                                        linearLayout.getLayoutParams().height = value.intValue();
                                        linearLayout.requestLayout();
                                    }
                                });
                                va.start();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean isBound = false;
    KeepAliveConnectionService service;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // get the service...
            service = ((KeepAliveConnectionService.LocalBinder) iBinder).getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    public void onEvent(OnLoginSuccessful loggedIn) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            openSelectTopic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        EventBus.getDefault().register(this);


        if (AuthManager.isLoggedIn(this)) {
            bindKeepAliveConnection();
        }
    }


    public void onEvent(OnUserLoggedIn event) {
        if (!isBound) {
            bindKeepAliveConnection();
        }
    }

    private void bindKeepAliveConnection() {
        Intent intent = new Intent(this, KeepAliveConnectionService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
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

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                final Geocoder geocoder = new Geocoder(getApplicationContext());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (addressList.size() > 0) {
                                        //Toast.makeText(getApplicationContext(), addressList.get(0).getCountryCode(),Toast.LENGTH_SHORT).show();
                                        getCountryMarkers(addressList.get(0).getCountryName());
                                        autoCompleteTextView.setText(addressList.get(0).getCountryName());
                                    } else
                                        Toast.makeText(getApplicationContext(), "No Country!", Toast.LENGTH_SHORT).show();
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

    private class UserPagerAdapter extends FragmentStatePagerAdapter {
        public UserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            UserPageFragment userPageFragment = new UserPageFragment();
            userPageFragment.setOnBottleClicked(bottleSelectionInterface);
            Bundle args = new Bundle();
            args.putInt("position", position);
            userPageFragment.setArguments(args);
            return userPageFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    ProgressDialog dialog = null;
    UserPageFragment.BottleSelectionInterface bottleSelectionInterface = new UserPageFragment.BottleSelectionInterface() {
        @Override
        public void onClick(int position) {
            if (AuthManager.isLoggedIn(MapsActivity.this)) {
                openSelectTopic();
            } else {
                CardView c = (CardView) findViewById(R.id.card_topic);
                c.setVisibility(View.VISIBLE);
                Fragment fragment = Fragment.instantiate(MapsActivity.this, LoginFragment.class.getName(), null);
                ((LoginFragment) fragment).setSignUpInterface(new LoginFragment.SignUpInterface() {
                    @Override
                    public void Successfull() {
                        dialog = new ProgressDialog(MapsActivity.this);
                        dialog.setMessage("Connecting..");
                        dialog.setCancelable(true);
                        dialog.show();
                    }
                });

                getSupportFragmentManager().beginTransaction().add(R.id.wrapper, fragment, null).commit();
            }
        }
    };

    private void openSelectTopic() {
        CardView c = (CardView) findViewById(R.id.card_topic);
        c.setVisibility(View.VISIBLE);
        Fragment fragment = Fragment.instantiate(MapsActivity.this, TopicSelectionFragment.class.getName(), null);
        getSupportFragmentManager().beginTransaction().add(R.id.wrapper, fragment).addToBackStack(null).commit();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
