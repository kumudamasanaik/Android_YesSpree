package com.yesspree.app.screens.payment;

import com.google.gson.JsonArray;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public interface IPaymentPresenter {

    void applyCoupons(String couponCode, String orderId,String header);
    void checkoutApi(String customerId, JsonArray jsonArray);
    void fetchSummery(String header,String callCheckSummery);
}
