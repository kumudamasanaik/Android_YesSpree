package com.yesspree.app.screens.otp;

import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.screens.common.IBaseView;

public interface IOtpView extends IBaseView {
    void setOtpVerifRes(ResponseModel res);

    void setOtpResendRes(ResponseModel res);
}
