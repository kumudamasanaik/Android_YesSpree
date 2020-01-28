package com.yesspree.app.screens.home;

import com.yesspree.app.modelapi.DashboardResModel;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.screens.common.IBaseView;

public interface IHomeView extends IBaseView {

    void callDashboardApi();

    void setDashboardApiRes(DashboardResModel dashboardApiRes);

    void setCartRes(FetchCartResponse cartRes);

    void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse);

    void setExploreViewResp(ResponseModel responseModel);

    void showViewState(int state);

}
