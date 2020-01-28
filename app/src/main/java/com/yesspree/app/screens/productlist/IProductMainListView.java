package com.yesspree.app.screens.productlist;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 08-05-2018.
 */

public interface IProductMainListView extends IBaseView {
    void CallProductListApi();

    void setProductListApiResponce(ProductMainListingRespModel dashboardApiRes);

    void setProductListRes(ProductMainListingRespModel dashboardApiRes);

    void setCartRes(FetchCartResponse cartRes);

    void setExploreViewResp(ResponseModel responseModel);

    void showViewState(int state);
}
