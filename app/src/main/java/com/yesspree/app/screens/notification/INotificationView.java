package com.yesspree.app.screens.notification;

import com.yesspree.app.modelapi.NotificationRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 03-05-2018.
 */

public interface INotificationView extends IBaseView {

    void getNotificationListData();
    void setNotificationResp(NotificationRespModel responseModel);
    void showViewState(int state);
}
