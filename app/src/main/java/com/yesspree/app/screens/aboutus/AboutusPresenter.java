package com.yesspree.app.screens.aboutus;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.AboutusRespModel;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public class AboutusPresenter implements IResponseInterface, IAboutusPresenter {
    private IAboutusView aboutusView;
    private IRequestInterface requestInterface;
    public String TAG = "HomePresenter";

    public AboutusPresenter(IAboutusView aboutusView) {
        this.aboutusView = aboutusView;
        this.requestInterface = new ApiResponsePresenter(this);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        aboutusView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.ABOUTUS: {
                    aboutusView.setAboutusApiResp((AboutusRespModel)response.body());
                }
                break;
            }
        } else
            aboutusView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        aboutusView.hideLoader();
        aboutusView.showViewState(MultiStateView.VIEW_STATE_ERROR);


    }

    @Override
    public void callAboutusApi() {
        requestInterface.callApi(AppController.getInstance().service.getAboutus(), ApiRequestTypes.ABOUTUS);

    }
}
