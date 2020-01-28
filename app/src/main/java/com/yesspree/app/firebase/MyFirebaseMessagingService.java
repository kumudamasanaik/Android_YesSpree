package com.yesspree.app.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

/**
 * Created by FuGenX-14 on 19-06-2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "MyFirebaseMessagingService";

    static Context mContext;
    RemoteMessage remoteMessage;

    static String body = "", imageURL = "", title = "";
    static Bitmap bitmap = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mContext = this;
        MyLogUtils.d(TAG, "onMessageReceived : " + remoteMessage.toString());
        this.remoteMessage = remoteMessage;

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            MyLogUtils.d(TAG, "Message data payload: " + remoteMessage.getData());
            setupDataPayLoadMsges();
        }
    }


    private void setupDataPayLoadMsges() {

        if (Validation.isValidString(remoteMessage.getData().get(Constants.FIREBASE_TITLE))) {
            title = remoteMessage.getData().get(Constants.FIREBASE_TITLE);
        }
        if (Validation.isValidString(remoteMessage.getData().get(Constants.FIREBASE_BODY))) {
            body = remoteMessage.getData().get(Constants.FIREBASE_BODY);
        }
        if (Validation.isValidString(remoteMessage.getData().get(Constants.FIREBASE_IMAGE))) {
            imageURL = remoteMessage.getData().get(Constants.FIREBASE_IMAGE);
            //bitmap = CommonUtils.getBitMapURL(imageURL);
            new GetBitMap().execute(imageURL);
        }
    }

    private static class GetBitMap extends AsyncTask<String, Integer, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return CommonUtils.getBitMapURL(urls[0]);
        }

        protected void onPostExecute(Bitmap bitmap) {
            NotificationHelper.getInstance(mContext).displayNotification(title, body, bitmap);
        }

    }


}

