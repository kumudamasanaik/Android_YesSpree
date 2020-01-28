package com.yesspree.app.screens.home;

import android.content.Context;

import com.yesspree.app.modelapi.CartModifyParam;

public interface IHomePresenter {

    void callDashboardApi(Context context);

    void modifyCart(CartModifyParam modifyParam);

    void modifyWishList(String mWishLIstType,String ProductId,String customerId,String Session);

    void callExploreClickApi(String explorId);

}
