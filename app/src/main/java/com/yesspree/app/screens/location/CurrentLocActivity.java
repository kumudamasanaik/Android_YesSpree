package com.yesspree.app.screens.location;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.addaddress.AddAddressActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.RealTimePermission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrentLocActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.layout_use_my_loc)
    View mFetchAddressButton;

    private static final String TAG = "CurrentLocActivity";
    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";
    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * Represents a geographical location.
     */
    private Location mLastLocation;
    /**
     * Tracks whether the user has requested an address. Becomes true when the user requests an
     * address and false when the address (or an error message) is delivered.
     */
    private boolean mAddressRequested;
    /**
     * The formatted location address.
     */
    private String mAddressOutput;
    private Address userCurAddress;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;
    Context context;
    String source = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        ButterKnife.bind(this);
        context = this;
        if (getIntent() != null) {
            source = getIntent().getExtras().getString(Constants.SOURCE);
        }
        mResultReceiver = new AddressResultReceiver(new Handler());
        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @OnClick(R.id.tv_set_loc_manually)
    void performSetManualLoc() {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_CURRENT_LOC);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!RealTimePermission.checkPermissionLocation(context)) {
            RealTimePermission.requestLocationPermission(context, RealTimePermission.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getAddress();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save whether the address has been requested.
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);

        // Save the address string.
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        RealTimePermission.displayLocationSettingsRequest(CurrentLocActivity.this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            MyLogUtils.e(TAG, "onSuccess:null");
                            return;
                        } else
                            mLastLocation = location;
                        // Determine whether a Geocoder is available.
                        if (!Geocoder.isPresent()) {
                            showSnackbar(getString(R.string.no_geocoder_available));
                            return;
                        }

                        // If the user pressed the fetch address button before we had the location,
                        // this will be set to true indicating that we should kick off the intent
                        // service after fetching the location.
                        if (mAddressRequested) {
                            startIntentService();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyLogUtils.w(TAG, "getLastLocation:onFailure", e);
                    }
                });
    }

    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * Toggles the visibility of the progress bar. Enables or disables the Fetch Address button.
     */
    /*private void updateUIWidgets() {
        if (mAddressRequested) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            mFetchAddressButton.setEnabled(false);
        } else {
            mProgressBar.setVisibility(ProgressBar.GONE);
            mFetchAddressButton.setEnabled(true);
        }
    }*/
    public void fetchAddressButtonHandler(View view) {
        if (!RealTimePermission.checkPermissionLocation(context)) {
            RealTimePermission.requestLocationPermission(context, RealTimePermission.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            if (mLastLocation != null) {
                startIntentService();
                return;
            } else {
                getAddress();
            }
            // If we have not yet retrieved the user location, we process the user's request by setting
            // mAddressRequested to true. As far as the user is concerned, pressing the Fetch Address button
            // immediately kicks off the process of getting the address.
            mAddressRequested = true;
        }
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Check savedInstanceState to see if the address was previously requested.
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            // Check savedInstanceState to see if the location address string was previously found
            // and stored in the Bundle. If it was found, display the address string in the UI.
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                //displayAddressOutput();
            }
        }
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mProgressBar.setVisibility(ProgressBar.GONE);
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            userCurAddress = resultData.getParcelable(Constants.RESULT_CURRENT_LOC);
            if (userCurAddress != null)
                saveAddress();
            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
        }
    }

    private void saveAddress() {
        AddressData addressData = new AddressData();
        addressData.setAddress1(userCurAddress.getAddressLine(0));
        addressData.setCity(userCurAddress.getLocality());
        addressData.setState(userCurAddress.getAdminArea());
        addressData.setCountry(userCurAddress.getCountryName());
        addressData.setPincode(userCurAddress.getPostalCode());
        userCurAddress.getFeatureName();
        SharedPreferenceManger.saveAddress(context, new Gson().toJson(addressData));
        if (source.equals(Constants.SOURCE_SPLASH))
            navigateToHome();
    }

    private void navigateToHome() {
        /*Intent intent = new Intent(context, DashBoardActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_CURRENT_LOC);
        startActivity(intent);*/
        // TODO Auto-generated method stub
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    private void displayAddressOutput() {
        CommonUtils.showToastMsg(context, mAddressOutput, 0);
    }


    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    private void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RealTimePermission.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAddress();
                }
        }
    }

}
