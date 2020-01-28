package com.yesspree.app.screens.sub_sub_category;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.SubSubCatResponseModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 10-05-2018.
 */

public interface ISub_SubCategoryView extends IBaseView {

    void setCategoryApiRes(SubSubCatResponseModel dashboardApiRes);
    void setCartRes(FetchCartResponse cartRes);

    void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse);


    void showViewState(int state);



}
