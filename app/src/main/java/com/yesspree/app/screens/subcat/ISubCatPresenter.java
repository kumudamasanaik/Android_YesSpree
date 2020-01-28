package com.yesspree.app.screens.subcat;

import com.yesspree.app.modelapi.CartModifyParam;

/**
 * Created by FuGenX-14 on 16-05-2018.
 */

public interface ISubCatPresenter {
    void callSubCatApi(String mCatId);

    void modifyCart(CartModifyParam modifyParam);

    void callExploreClickApi(String explorId,String type);

    void modifyWishList(String mWishLIstType,String ProductId,String customerId,String Session);

    void callChildCataData(String catId);

}
