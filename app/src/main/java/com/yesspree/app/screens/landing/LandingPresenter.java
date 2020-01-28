package com.yesspree.app.screens.landing;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.UserData;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class LandingPresenter implements ILandingPresenter, IResponseInterface {

    private final ILandingView view;
    IRequestInterface iRequest;

    public LandingPresenter(ILandingView view) {
        this.view = view;
        this.iRequest = new ApiResponsePresenter(this);
    }

    @Override
    public void doSocialsignUp(SocialSignInputModel inputModel) {
        iRequest.callApi(AppController.getInstance().service.socialsignUp(ApiRequestParam.getInstance().socialsignUpParams(inputModel)), ApiRequestTypes.SOCIAL_SIGNUP);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (response != null) {
            switch (reqType) {
                case ApiRequestTypes.SOCIAL_SIGNUP: {
                    view.socailLoginRes((UserData) response.body());
                }
            }
        } else
            view.showMsg(null);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        view.showMsg(responseError.getMessage());
    }
}
