package com.yesspree.app.screens.myaccount;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.MyAccountRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.screens.signup.SignUpModelToValidation;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public class MyAccountPresenter implements IResponseInterface, IMyAccountPresenter {

    private IMyAccountView myAccountView;
    private IRequestInterface requestInterface;
    public String TAG = "MyAccountPresenter";

    public MyAccountPresenter(IMyAccountView myAccountView) {
        this.myAccountView = myAccountView;
        this.requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        myAccountView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.MY_ACCOUNT: {
                    myAccountView.setMyAccountDetails((MyAccountRespModel) response.body());
                }
                break;
                case ApiRequestTypes.UPDATE_ACCOUNT: {
                    myAccountView.updateAccountDetailsResp((MyAccountRespModel) response.body());
                }
                break;
            }
        } else
            myAccountView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        myAccountView.hideLoader();

        switch (reqType) {
            case ApiRequestTypes.MY_ACCOUNT: {
                myAccountView.showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
            break;
            case ApiRequestTypes.UPDATE_ACCOUNT: {
                myAccountView.showMsg(responseError.getMessage());
            }
            break;

        }
    }

    @Override
    public void callMyAcountDetails(String authKey) {
        requestInterface.callApi(AppController.getInstance().service.getMyAccountDetails(authKey), ApiRequestTypes.MY_ACCOUNT);
    }

    @Override
    public void saveUpdatedAccountDetails(SignUpModelToValidation signUpModelToValidation) {
        requestInterface.callApi(AppController.getInstance().service.saveMyAccountDetails(ApiRequestParam.getInstance().getUpdateMyAccount(signUpModelToValidation)), ApiRequestTypes.UPDATE_ACCOUNT);

    }
}
