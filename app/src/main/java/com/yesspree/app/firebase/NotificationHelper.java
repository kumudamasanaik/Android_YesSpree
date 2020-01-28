package com.yesspree.app.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;

import com.yesspree.app.R;
import com.yesspree.app.screens.notification.NotificationActivity;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by FuGenX-14 on 19-06-2018.
 */

public class NotificationHelper {
    private String TAG = "NotificationHelper ";
    public static final String CHANNEL_ID = " YESPREE ";
    public static final String CHANNEL_NAME = "YESPREE SIMPLE NOTIFICATION";
    private static NotificationHelper mInstance = null;
    private Context mContext;
    String GROUP_KEY_ESPREE = "YESPREE GROUP";
    int NotificationId=1;


    private NotificationHelper(Context context) {
        mContext = context;
    }

    public static synchronized NotificationHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotificationHelper(context);
        }
        return mInstance;
    }


    public void displayNotification(String title, String body, Bitmap bitmap) {
        MyLogUtils.d(TAG, "Refreshed token  " + "Title : " + title + "\nbody : " + body);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(body);
            mChannel.setName(title);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        } else {

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "CHANNEL 1");
            mBuilder.setSmallIcon(R.drawable.logo_light_bg);
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(body);
            mBuilder.setGroup(GROUP_KEY_ESPREE);
            mBuilder.setGroupSummary(true);
            Bitmap largeIcon = null;
            if (Validation.isValidObject(bitmap)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Drawable drawable = mContext.getDrawable(R.drawable.notification_logo);
                    largeIcon = ((BitmapDrawable) drawable).getBitmap();
                    mBuilder.setLargeIcon(largeIcon);

                } else {
                    mBuilder.setLargeIcon(bitmap);
                }
                mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(largeIcon));
            }

            Intent resultIntent = new Intent(mContext, NotificationActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            if (mNotifyMgr != null) {
                mNotifyMgr.notify(NotificationId, mBuilder.build());
                NotificationId++;
            }
        }
    }


}
