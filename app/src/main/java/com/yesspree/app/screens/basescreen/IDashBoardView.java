package com.yesspree.app.screens.basescreen;

import com.yesspree.app.modelapi.RateUsRes;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.screens.common.IBaseView;

public interface IDashBoardView extends IBaseView {

    void setUpNavigationDrawer();

    void setUpBottomNavigation();

    void setRateUsRes(RateUsRes res);

    void sendServerFirebaseToken();

    void setFirebaseTokenResp(ResponseModel resp);
}
