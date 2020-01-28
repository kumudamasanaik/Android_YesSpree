package com.yesspree.app.screens.notification;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.NotificationRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 03-05-2018.
 */

public class NotificationPresenter implements IResponseInterface,INotificationPresenter{
    private INotificationView notificationView;
    private IRequestInterface requestInterface;
    public String TAG = "NotificationPresenter";

    public NotificationPresenter(INotificationView notificationView) {
        this.notificationView = notificationView;
        this.requestInterface = new ApiResponsePresenter(this);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        notificationView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.NOTIFICATION_LIST: {
                    notificationView.setNotificationResp((NotificationRespModel) response.body());
                }
                break;
            }
        } else
            notificationView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        notificationView.hideLoader();
        notificationView.showViewState(MultiStateView.VIEW_STATE_ERROR);


    }

    @Override
    public void callNotificationApi(String custID) {
        requestInterface.callApi(AppController.getInstance().service.getNotificationList(ApiRequestParam.getInstance().getNotificationData(custID)), ApiRequestTypes.NOTIFICATION_LIST);

    }
}
