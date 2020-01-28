package com.yesspree.app.screens.otp;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class OtpPresenter implements IOtpPresenter, IResponseInterface {
    private final IRequestInterface requestInterface;
    IOtpView view;

    public OtpPresenter(IOtpView view) {
        this.view = view;
        requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void verifyOtp(String mblEmail, String otp) {
        requestInterface.callApi(AppController.getInstance().service.verifyOtp( ApiRequestParam.getInstance().verifyOtp(otp,mblEmail)), ApiRequestTypes.VERIFY_OTP);
    }

    @Override
    public void resenOtp(String header) {
        requestInterface.callApi(AppController.getInstance().service.resendOtp(header), ApiRequestTypes.RESEND_OTP);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (response != null && response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.VERIFY_OTP:
                    view.setOtpVerifRes((ResponseModel) response.body());
                    break;
                case ApiRequestTypes.RESEND_OTP:
                    view.setOtpResendRes((ResponseModel) response.body());
                    break;
            }
        } else
            view.showMsg("empty");
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        view.showMsg(responseError.getMessage());
    }
}
