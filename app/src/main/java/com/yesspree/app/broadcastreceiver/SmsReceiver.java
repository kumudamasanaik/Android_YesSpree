package com.yesspree.app.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.utility.MyLogUtils;


public class SmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String otp = currentMessage.getDisplayMessageBody();//.split(":")[1];
                    otp = otp.substring(otp.length() - 4, otp.length());
                    MyLogUtils.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + otp + " ; Full Message " + currentMessage.getDisplayMessageBody());
                    Intent myIntent = new Intent();
                    myIntent.setAction(Constants.OTP_BROADCAST_INTENT);
                    myIntent.putExtra(Constants.OTP, otp);
                    context.sendBroadcast(myIntent);
                }
            }
        } catch (Exception e) {
            MyLogUtils.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}