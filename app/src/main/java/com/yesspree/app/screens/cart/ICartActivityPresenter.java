package com.yesspree.app.screens.cart;

import com.yesspree.app.modelapi.CartModifyParam;

/**
 * Created by FuGenX-14 on 07-05-2018.
 */

public interface ICartActivityPresenter {

    void fetchCart(String id, String session);
    void modifyCart(CartModifyParam modifyParam);
    void removeFromCart(CartModifyParam modifyParam);
    void callExploreClickApi(String explorId);
}
