package com.yesspree.app.screens.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.landing.LandingActivity;
import com.yesspree.app.screens.location.CurrentLocActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

public class SplashActivity extends AppCompatActivity {

    private Runnable runnable;
    private Handler handler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigate();
    }

    private void navigateToHome() {
        Intent intent = new Intent(context, DashBoardActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH);
        startActivity(intent);
        finish();
    }

    private void navigateToLocScreen() {
        Intent intent = new Intent(context, CurrentLocActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH);
        startActivity(intent);
        finish();
    }

    private void navigate() {
        // TODO: 06-06-2018  remove pincode..
        CommonUtils.setPincode(context, "560075");
        runnable = () -> {
            if (CommonUtils.isLoggedIn(context) && Validation.isValidObject(SharedPreferenceManger.getAddressData(context)))
                navigateToHome();
            else
                naviagateLandingScreen();
            /*  if (Validation.isValidObject(SharedPreferenceManger.getAddressData(context)))
                navigateToHome();
            else
                navigateToLocScreen();
*/
        };
        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }

    private void naviagateLandingScreen() {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH);
        startActivity(intent);
        finish();
    }
}
