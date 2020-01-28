package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 20-06-2018.
 */

public class NotificationRespModel extends ResponseModel {

    @SerializedName(Constants.RESULT)
    private ArrayList<Notification> notificationArrayList;

    public ArrayList<Notification> getNotificationArrayList() {
        return notificationArrayList;
    }

    public void setNotificationArrayList(ArrayList<Notification> notificationArrayList) {
        this.notificationArrayList = notificationArrayList;
    }
}
