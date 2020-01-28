package com.yesspree.app.screens.myorder;

/**
 * Created by FuGenX-14 on 08-06-2018.
 */

public interface IMyOrdersPresenter {
    void callMyOrdersListApi(String type, String authorisationKey);
    void callCancelOrderApi(String orderNo);
}
