package com.yesspree.app.screens.productlist;

import com.yesspree.app.modelapi.CartModifyParam;

/**
 * Created by FuGenX-14 on 08-05-2018.
 */

public interface IProductMainListPresenter {
    void callMainProductListApi(String params,int type);
    void callExploreClickApi(String explorId);
    void callProductList(String params,int type);
    void modifyCart(CartModifyParam modifyParam);
}
