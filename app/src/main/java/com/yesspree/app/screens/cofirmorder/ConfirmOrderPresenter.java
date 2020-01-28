package com.yesspree.app.screens.cofirmorder;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.utility.Validation;

import retrofit2.Call;
import retrofit2.Response;

public class ConfirmOrderPresenter implements IConfirmOrderPresenter, IResponseInterface {

    IRequestInterface requestInterface;
    IConfirmOrderView view;


    public ConfirmOrderPresenter(IConfirmOrderView view) {
        this.view = view;
        requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void fetchOrderSummery(String session, String pincode, String authkey) {
        requestInterface.callApi(AppController.getInstance().service.getOrderSummery(authkey, ApiRequestParam.getInstance().getOrderSummery(session, pincode)), ApiRequestTypes.ORDER_SUMMERY);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (Validation.isValidObject(response) && response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.ORDER_SUMMERY:
                    view.setOrderSummeryRes((FetchCartSummaryResponse) response.body());
                    break;
            }
        } else
            view.setOrderSummeryRes(null);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.ORDER_SUMMERY:
                view.setOrderSummeryRes(null);
                break;
        }
    }
}
