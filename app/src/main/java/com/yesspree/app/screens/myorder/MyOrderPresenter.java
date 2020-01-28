package com.yesspree.app.screens.myorder;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 08-06-2018.
 */

public class MyOrderPresenter implements IResponseInterface, IMyOrdersPresenter {
    IMyOrdersView myOrdersView;
    private IRequestInterface requestInterface;

    public MyOrderPresenter(IMyOrdersView iMyOrdersView) {
        this.myOrdersView = iMyOrdersView;
        this.requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void callMyOrdersListApi(String type, String authorised_key) {
        requestInterface.callApi(AppController.getInstance().service.getMyOrderList(authorised_key, ApiRequestParam.getInstance().getMyOrdersList(type)), ApiRequestTypes.MY_ORDERS);
    }

    @Override
    public void callCancelOrderApi(String orderNo) {
        requestInterface.callApi(AppController.getInstance().service.cancelOrder(ApiRequestParam.getInstance().cancelOrder(orderNo)), ApiRequestTypes.CANCEL_ORDER);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {

        myOrdersView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.MY_ORDERS:
                    myOrdersView.setMyOrderListRes((MyOrdersRespModel) response.body());
                    break;
                case ApiRequestTypes.CANCEL_ORDER:
                    myOrdersView.setCancelOrderRes((CancelOrder) response.body());
                    break;
            }
        } else
            myOrdersView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        myOrdersView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.MY_ORDERS:
                myOrdersView.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
        }
    }


}
