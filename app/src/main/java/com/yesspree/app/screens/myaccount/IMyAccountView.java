package com.yesspree.app.screens.myaccount;

import com.yesspree.app.modelapi.MyAccountRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public interface IMyAccountView extends IBaseView {

    void getMyAccountDetails();
    void setMyAccountDetails(MyAccountRespModel responseModel);
    void updateAccountDetailsResp(MyAccountRespModel responseModel);
    void showViewState(int state);
}
