package com.yesspree.app.screens.wishlist;

import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 07-06-2018.
 */

public interface IWishListView extends IBaseView {
    void setWishListApiResponce(ModifyWishlistResponse modifyWishlistResponse);
    void callWishListApi();
    void showViewState(int state);
    void setCartRes(FetchCartResponse cartRes);
    void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse);
}
