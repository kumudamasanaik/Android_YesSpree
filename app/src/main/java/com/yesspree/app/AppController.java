package com.yesspree.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yesspree.app.apipresenter.RestApi;
import com.yesspree.app.broadcastreceiver.NetworkReceiver;
import com.yesspree.app.network.ApiConstants;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = "AppController";
    public static AppController mInstance;
    public static Context context;
    public static Activity mActivity;
    public RestApi service;
    NetworkReceiver mNetworkReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        enableStrictMode();
        registerActivityLifecycleCallbacks(this);
        createRetrofitObj();
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    private void createRetrofitObj() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestApi.class);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    private OkHttpClient getRequestHeader() {
        OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder();
        okhttpClient.readTimeout(10, TimeUnit.SECONDS);
        okhttpClient.writeTimeout(10, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttpClient.addNetworkInterceptor(new StethoInterceptor());
            okhttpClient.addInterceptor(logging);
        }
        return okhttpClient.build();
    }


    public String getPinCode() {
        return CommonUtils.getPincode(this);
    }

    public String getSession() {
        return CommonUtils.getSession(this);
    }

    public String getCustomerId() {
        return CommonUtils.getCustomerId(this);
    }

    public String getAuthKey() {
        return CommonUtils.getAuthorizationKey(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (Validation.isValidObject(activity) && activity.getPackageName().contains("com.yesspree.app")) {
            MyLogUtils.i(TAG, "onActivityCreated:" + activity.getClass().getSimpleName());
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            mNetworkReceiver = new NetworkReceiver();

            final Fabric fabric = new Fabric.Builder(this)
                    .kits(new Crashlytics())
                    .debuggable(true)           // Enables Crashlytics debugger
                    .build();
            Fabric.with(fabric);
        } else
            MyLogUtils.i(TAG, "Out of package onActivityCreated:" + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mActivity = activity;
        MyLogUtils.i(TAG, "onActivityStarted:" + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (Validation.isValidObject(activity) && activity.getPackageName().contains("com.yesspree.app")) {
            mActivity = activity;
            registerNetworkBroadcastForNougat();
            MyLogUtils.i(TAG, "onActivityResumed:" + activity.getClass().getSimpleName());
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (Validation.isValidObject(activity) && activity.getPackageName().contains("com.yesspree.app")) {
            mActivity = null;
            unregisterReceiver(mNetworkReceiver);
            MyLogUtils.i(TAG, "onActivityPaused:" + activity.getClass().getSimpleName());
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        MyLogUtils.i(TAG, "onActivityPaused:" + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        MyLogUtils.i(TAG, "onActivitySaveInstanceState:" + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        MyLogUtils.i(TAG, "onActivityDestroyed:" + activity.getClass().getSimpleName());
    }

    private void registerNetworkBroadcastForNougat() {
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
