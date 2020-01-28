package com.yesspree.app.screens.myorderstatus;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.MyOrderStatusRes;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.utility.Validation;

import retrofit2.Call;
import retrofit2.Response;

public class MyOrderStatusPresenter implements IMyOrderStatusPresenter, IResponseInterface {

    IMyOrderStatusView view;
    IRequestInterface iRequestInterface;

    public MyOrderStatusPresenter(IMyOrderStatusView view) {
        this.view = view;
        iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void getMyOrderStatus(String orderNo) {
        iRequestInterface.callApi(AppController.getInstance().service.getMyOrderStatus(ApiRequestParam.getInstance().getMyOrdersStatus(orderNo)), ApiRequestTypes.MY_ORDERS_STATUS);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (Validation.isValidObject(response) && response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.MY_ORDERS_STATUS:
                    view.setMyOrderRes((MyOrderStatusRes) response.body());
                    break;
            }
        } else
            view.showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        view.showViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
