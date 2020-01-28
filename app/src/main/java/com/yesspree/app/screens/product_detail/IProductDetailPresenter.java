package com.yesspree.app.screens.product_detail;

import com.yesspree.app.modelapi.CartModifyParam;

/**
 * Created by FuGenX-14 on 02-05-2018.
 */

public interface IProductDetailPresenter {
    void callProductDetailApi(String mCatId);

    void modifyCart(CartModifyParam modifyParam);

    void modifyWishList(String mWishLIstType,String ProductId,String customerId,String Session);


}
