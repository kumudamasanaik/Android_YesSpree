package com.yesspree.app.screens.wishlist;

import com.yesspree.app.modelapi.CartModifyParam;

/**
 * Created by FuGenX-14 on 07-06-2018.
 */

public interface IWishListPresenter {
    void callWishListApi(String cusId);
    void modifyCart(CartModifyParam modifyParam);
    void modifyWishList(String mWishLIstType,String ProductId,String customerId,String Session);
}
