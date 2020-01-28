package com.yesspree.app.screens.offers;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.OffersResModel;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 02-05-2018.
 */

public class OffersPresenter implements IResponseInterface,IOffersPresenter {

    private IOffersView offersView;
    private IRequestInterface requestInterface;
    public String TAG = "OffersPresenter";

    public OffersPresenter(IOffersView offersView) {
        this.offersView = offersView;
        this.requestInterface = new ApiResponsePresenter(this);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        offersView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.OFFERS_LIST: {
                    offersView.setOffersResp((OffersResModel) response.body());
                }
                break;
            }
        } else
            offersView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        offersView.hideLoader();
        offersView.showViewState(MultiStateView.VIEW_STATE_ERROR);


    }

    @Override
    public void callOffersApi() {
        requestInterface.callApi(AppController.getInstance().service.getOffersLIst(), ApiRequestTypes.OFFERS_LIST);

    }
}

