package com.yesspree.app.screens.basescreen;

import android.content.Context;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.RateUsInputModel;
import com.yesspree.app.modelapi.RateUsRes;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class DashBoardPresenter implements IDashBoardPresenter, IResponseInterface {
    IDashBoardView dashBoardView;
    IRequestInterface iRequestInterface;

    public DashBoardPresenter(IDashBoardView dashBoardView) {
        this.dashBoardView = dashBoardView;
        iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void submitRatings(RateUsInputModel rateUsInputModel) {
        iRequestInterface.callApi(AppController.getInstance().service.submitRatings(rateUsInputModel), ApiRequestTypes.RATE_US);
    }

    @Override
    public void sendFirebaseTokenToServer(Context mCtxt) {
        iRequestInterface.callApi(AppController.getInstance().service.getFireBaseTokenResp(ApiRequestParam.getInstance().getFirebaseToken(mCtxt)), ApiRequestTypes.REG_FIRBASE_TOKEN);

    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        dashBoardView.hideLoader();
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.RATE_US:
                    dashBoardView.setRateUsRes((RateUsRes) response.body());
                    break;

                case ApiRequestTypes.REG_FIRBASE_TOKEN: {
                    dashBoardView.setFirebaseTokenResp((ResponseModel) response.body());
                }
                break;
            }
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        dashBoardView.hideLoader();
    }
}
