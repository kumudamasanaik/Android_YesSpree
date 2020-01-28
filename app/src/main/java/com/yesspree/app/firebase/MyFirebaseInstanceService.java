package com.yesspree.app.firebase;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;

/**
 * Created by FuGenX-14 on 19-06-2018.
 */

public class MyFirebaseInstanceService extends FirebaseInstanceIdService {
    private Context mContext;
    private String TAG = "MyFirebaseInstanceService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        mContext = this;
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        CommonUtils.setFireBaseTokenID(mContext, refreshedToken);
        MyLogUtils.i(TAG, "Refreshed token  " + refreshedToken);


    }
}
