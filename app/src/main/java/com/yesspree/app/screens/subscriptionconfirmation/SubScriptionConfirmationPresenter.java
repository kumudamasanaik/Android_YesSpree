package com.yesspree.app.screens.subscriptionconfirmation;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.AddSubcrInput;
import com.yesspree.app.modelapi.SubscribeProdRes;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 05-07-2018.
 */

public class SubScriptionConfirmationPresenter implements ISubScriptionConfirmationPresenter, IResponseInterface {
    ISubScriptionConfirmationView view;
    IRequestInterface iRequestInterface;

    public SubScriptionConfirmationPresenter(ISubScriptionConfirmationView view) {
        this.view = view;
        iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void addSubscribe(String header, AddSubcrInput input) {
        iRequestInterface.callApi(AppController.getInstance().service.subscribe(header, input), ApiRequestTypes.SUBSCRIPTION);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.SUBSCRIPTION:
                    view.setSubScriptionConfirmAPiResp((SubscribeProdRes) response.body());
                    break;
            }
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        view.setSubScriptionConfirmAPiResp(null);
    }
}
