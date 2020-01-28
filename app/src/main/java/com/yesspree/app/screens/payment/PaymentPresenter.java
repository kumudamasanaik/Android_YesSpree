package com.yesspree.app.screens.payment;

import com.google.gson.JsonArray;
import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.ApplyCouponRes;
import com.yesspree.app.modelapi.CheckoutArrayResponse;
import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.network.ApiConstants;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.utility.Validation;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public class PaymentPresenter implements IPaymentPresenter, IResponseInterface {
    IPaymentView view;
    private IRequestInterface requestInterface;
    private CheckoutArrayResponse customRes;

    public PaymentPresenter(IPaymentView view) {
        this.view = view;
        requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void applyCoupons(String couponCode, String orderId, String header) {
        requestInterface.callApi(AppController.getInstance().service.applyCoupon(header, ApiRequestParam.getInstance().applyCoupon(couponCode, orderId)), ApiRequestTypes.APPLY_COUPON);
    }

    @Override
    public void checkoutApi(String customerId, JsonArray jsonArray) {
        requestInterface.callApi(AppController.getInstance().service.checkoutApi(ApiRequestParam.getInstance().checkoutApi(customerId, jsonArray)), ApiRequestTypes.CHECK_OUT);
    }

    @Override
    public void fetchSummery(String header, String couponCode) {
        requestInterface.callApi(AppController.getInstance().service.getOrderSummery(header, ApiRequestParam.getInstance().getOrderSummery(couponCode)), ApiRequestTypes.ORDER_SUMMERY);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (Validation.isValidObject(response) && response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.CHECK_OUT:
                    view.setCheckoutRes((CheckoutArrayResponse) response.body());
                    break;
                case ApiRequestTypes.APPLY_COUPON:
                    view.setApplyCouponRes((ApplyCouponRes) response.body());
                    break;
                case ApiRequestTypes.ORDER_SUMMERY:
                    view.setOrderSummeryRes((FetchCartSummaryResponse) response.body());
                    break;

            }
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        customRes = new CheckoutArrayResponse();
        customRes.setThrowable(responseError);
        view.hideLoader();
        //view.setCheckoutRes(customRes);
    }
}
