package com.yesspree.app.screens.subcat;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SubCategoryResModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 16-05-2018.
 */

public interface ISubCatView extends IBaseView {
    void callSubCatApi();

    void callChildCatagoryData(String childCatID);

    void setSubCatApiRes(SubCategoryResModel dashboardApiRes);

    void setCartRes(FetchCartResponse cartRes);

    void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse);

    void setExploreViewResp(ResponseModel responseModel,String mExploretype);

    void setChildCatApiResponce(MultipleCatRespModel res);

    void showViewState(int state);

}
