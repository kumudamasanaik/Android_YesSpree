package com.yesspree.app.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.CartSummaryData;

public class SharedPreferenceManger {

    public static String APP_PREF = "yesspressPref";

    public static SharedPreferences getSharedPreferences(Context mContext) {
        return mContext.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public static void clearPreferences(Context mContext) {
        SharedPreferences pref = getSharedPreferences(mContext);
        Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }

    public static void saveCartData(Context context, String cartData) {
        SharedPreferenceManger.setPrefVal(context, Constants.CART_DATA_PREF, cartData, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static CartSummaryData getCartData(Context context) {
        String json = (String) SharedPreferenceManger.getPrefVal(context, Constants.CART_DATA_PREF, null, SharedPreferenceManger.VALUE_TYPE.STRING);
        if (json != null) {
            return (CartSummaryData) new Gson().fromJson(json, CartSummaryData.class);
        }
        return null;
    }

    public static void saveAddress(Context context, String address) {
        SharedPreferenceManger.setPrefVal(context, Constants.SAVE_ADDRESS, address, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static AddressData getAddressData(Context context) {
        String json = (String) SharedPreferenceManger.getPrefVal(context, Constants.SAVE_ADDRESS, null, SharedPreferenceManger.VALUE_TYPE.STRING);
        if (json != null) {
            return (AddressData) new Gson().fromJson(json, AddressData.class);
        }
        return null;
    }

    public static void setPrefVal(Context mContext, String key, Object value, VALUE_TYPE vType) {
        SharedPreferences pref = getSharedPreferences(mContext);
        Editor edit = pref.edit();
        switch (vType) {
            case BOOLEAN:
                edit.putBoolean(key, (Boolean) value);
                break;
            case INTEGER:
                edit.putInt(key, (Integer) value);
                break;
            case STRING:
                edit.putString(key, (String) value);
                break;
            case FLOAT:
                edit.putFloat(key, (Float) value);
                break;
            case LONG:
                edit.putLong(key, (Long) value);
                break;
            default:
                break;
        }
        edit.commit();
    }

    public static Object getPrefVal(Context mContext, String key, Object defValue, VALUE_TYPE vType) {
        Object object;
        SharedPreferences pref = getSharedPreferences(mContext);
        switch (vType) {
            case BOOLEAN:
                object = pref.getBoolean(key, (Boolean) defValue);
                break;
            case INTEGER:
                object = pref.getInt(key, (Integer) defValue);
                break;
            case STRING:
                object = pref.getString(key, (String) defValue);
                break;
            case FLOAT:
                object = pref.getFloat(key, (Float) defValue);
                break;
            case LONG:
                object = pref.getLong(key, (Long) defValue);
                break;
            default:
                throw new NullPointerException();
        }
        return object;
    }

    public enum VALUE_TYPE {
        BOOLEAN, INTEGER, STRING, FLOAT, LONG
    }
}
