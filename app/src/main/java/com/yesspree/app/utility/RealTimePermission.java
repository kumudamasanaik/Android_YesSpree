package com.yesspree.app.utility;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.yesspree.app.R;
import com.yesspree.app.screens.location.CurrentLocActivity;

public class RealTimePermission {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int SMS_PERMISSION_REQUEST_CODE = 3;
    public static final int REQUEST_CHECK_SETTINGS = 4;
    public static String TAG = "RealTimePermission";

    public static boolean checkPermissionLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public static boolean checkPermissionStorage(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public static boolean checkPermissionSMS(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public static void requestLocationPermission(final Context context, final int PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            final Dialog myDialog = new Dialog(context, R.style.CustomDialogThemeLightBg);
            myDialog.setCanceledOnTouchOutside(true);
            myDialog.setContentView(R.layout.access_permission_dialog);
            ((TextView) myDialog.findViewById(R.id.dialog_title)).setText("Access your Location");
            ((TextView) myDialog.findViewById(R.id.dialog_text)).setText(context.getString(R.string.access_location_msg));
            myDialog.findViewById(R.id.dialog_btn_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                    myDialog.dismiss();
                }
            });

            myDialog.findViewById(R.id.dialog_btn_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        } else {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void requestStoragePermission(final Context context, final int PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            final Dialog myDialog = new Dialog(context, R.style.CustomDialogThemeLightBg);
            myDialog.setCanceledOnTouchOutside(true);
            myDialog.setContentView(R.layout.access_permission_dialog);
            ((TextView) myDialog.findViewById(R.id.dialog_title)).setText("Access Storage");
            ((TextView) myDialog.findViewById(R.id.dialog_text)).setText(context.getString(R.string.access_storage_msg));
            myDialog.findViewById(R.id.dialog_btn_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    myDialog.dismiss();
                }
            });

            myDialog.findViewById(R.id.dialog_btn_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        } else {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void requestSMSPermission(final Context context, final int PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context, Manifest.permission.READ_SMS)) {
            final Dialog myDialog = new Dialog(context, R.style.CustomDialogThemeLightBg);
            myDialog.setCanceledOnTouchOutside(true);
            myDialog.setContentView(R.layout.access_permission_dialog);
            ((TextView) myDialog.findViewById(R.id.dialog_title)).setText(context.getString(R.string.read_sms));
            ((TextView) myDialog.findViewById(R.id.dialog_text)).setText(context.getString(R.string.access_sms_msg));
            myDialog.findViewById(R.id.dialog_btn_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
                    myDialog.dismiss();
                }
            });

            myDialog.findViewById(R.id.dialog_btn_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        } else {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void checkLocationSettings(ResultCallback<LocationSettingsResult> context, GoogleApiClient mGoogleApiClient, LocationRequest locationRequest) {
        // Check the location settings of the user and create the callback to react to the different possibilities
        LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequestBuilder.build());
        result.setResultCallback(context);
    }

    public static void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        MyLogUtils.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        MyLogUtils.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult((Activity) context, LOCATION_PERMISSION_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            MyLogUtils.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        MyLogUtils.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}
