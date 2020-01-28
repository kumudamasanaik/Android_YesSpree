package com.yesspree.app.screens.productlist.productlist_fragment;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.screens.common.IBaseView;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 08-05-2018.
 */

public interface IProductListFragmentView extends IBaseView {
    void showViewState(int state);

    void setProductList(ProductMainListingRespModel productListRes);

    void setPaginatedProductList(ProductMainListingRespModel respModel);

    void setCartRes(FetchCartResponse cartRes);

    void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse);


}
