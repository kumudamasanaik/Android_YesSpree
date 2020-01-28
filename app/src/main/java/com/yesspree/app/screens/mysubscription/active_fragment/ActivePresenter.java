package com.yesspree.app.screens.mysubscription.active_fragment;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public class ActivePresenter implements IActivePresenter, IResponseInterface {
    IActiveView view;
    IRequestInterface iRequestInterface;

    public ActivePresenter(IActiveView view) {
        this.view = view;
        iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void getSubscritionList(String header, String type) {
        iRequestInterface.callApi(AppController.getInstance().service.mySubscribtion(header, ApiRequestParam.getInstance().getSubsscription(type)), ApiRequestTypes.MY_SUBSCRIPTIONS);
    }

    @Override
    public void cancelSubscrition(String orderNum) {
        iRequestInterface.callApi(AppController.getInstance().service.cancelSubscr(ApiRequestParam.getInstance().cancelSubscr(orderNum)), ApiRequestTypes.CANCEL_SUBSCRIPTIONS);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.MY_SUBSCRIPTIONS:
                    view.setMySubscrRes((MyOrdersRespModel) response.body());
                    break;
                case ApiRequestTypes.CANCEL_SUBSCRIPTIONS:
                    view.setCancelSubscrRes((CancelOrder) response.body());
                    break;

            }
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.MY_SUBSCRIPTIONS:
                view.setMySubscrRes(null);
                break;
            case ApiRequestTypes.CANCEL_SUBSCRIPTIONS:
                view.setCancelSubscrRes(null);
                break;
        }
    }
}
