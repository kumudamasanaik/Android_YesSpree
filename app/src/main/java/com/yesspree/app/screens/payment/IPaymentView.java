package com.yesspree.app.screens.payment;

import com.yesspree.app.modelapi.ApplyCouponRes;
import com.yesspree.app.modelapi.CheckoutArrayResponse;
import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public interface IPaymentView extends IBaseView {
    void callCheckOutApi();

    void setCheckoutRes(CheckoutArrayResponse response);

    void setApplyCouponRes(ApplyCouponRes response);

    void callCheckSummery(String couponCode);

    void setOrderSummeryRes(FetchCartSummaryResponse res);

    void showViewState(int state);
}
