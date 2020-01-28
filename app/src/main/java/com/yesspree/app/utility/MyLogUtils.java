package com.yesspree.app.utility;

import android.util.Log;

import com.yesspree.app.BuildConfig;

public class MyLogUtils {

    public static boolean isDebug = BuildConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (isDebug) {
            if (Validation.isValidString(tag) && Validation.isValidString(msg))
                Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            if (Validation.isValidString(tag) && Validation.isValidString(msg))
                Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg,Throwable t) {
        if (isDebug) {
            if (Validation.isValidString(tag) && Validation.isValidString(msg))
                Log.e(tag, msg,t);
        }
    }


    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug) {
            if (Validation.isValidString(tag) && Validation.isValidString(msg))
                Log.w(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            if (Validation.isValidString(tag) && Validation.isValidString(msg))
                Log.i(tag, msg);
        }
    }

    public static void wtf(String tag, String msg) {
        if (isDebug) {
            if (Validation.isValidString(tag) && Validation.isValidString(msg))
                Log.wtf(tag, msg);
        }
    }
}
