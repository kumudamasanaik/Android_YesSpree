package com.yesspree.app.screens.cart;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 07-05-2018.
 */

public interface ICartActivityView extends IBaseView {


    void callfetchCartApi();

    void setFetchRes(FetchCartResponse fetchRes);

    void setModifyCart(FetchCartResponse fetchRes);

    void setRemoveFromCart(FetchCartResponse fetchRes);

    void setExploreViewResp(ResponseModel responseModel);

    void showViewState(int state);
}
