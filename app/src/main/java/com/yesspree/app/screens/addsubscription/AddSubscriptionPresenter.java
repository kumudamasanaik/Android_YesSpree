package com.yesspree.app.screens.addsubscription;

import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class AddSubscriptionPresenter implements IResponseInterface, IAddSubscriptionPresenter {
    IAddSubscriptionView view;
    IRequestInterface iRequestInterface;


    public AddSubscriptionPresenter(IAddSubscriptionView view) {
        this.view = view;
        iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.SUBSCRIPTION:
                    break;
            }
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {

    }
}
