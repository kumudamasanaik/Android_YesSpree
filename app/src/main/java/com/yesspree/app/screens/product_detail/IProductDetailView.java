package com.yesspree.app.screens.product_detail;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductDetailRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 02-05-2018.
 */

public interface IProductDetailView extends IBaseView {

    void callProductDetailApi();

    void setProductDetailApiResponce(ProductDetailRespModel dashboardApiRes);
    void callProductCartApi();
    void setCartRes(FetchCartResponse cartRes);

    void callModifyWishListApi();

    void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse);



    void showViewState(int state);
}

