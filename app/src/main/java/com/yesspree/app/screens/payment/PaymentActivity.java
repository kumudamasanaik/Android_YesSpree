package com.yesspree.app.screens.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.modelapi.ApplyCouponRes;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.modelapi.CheckoutArrayResponse;
import com.yesspree.app.modelapi.CheckoutOrderData;
import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.modelapi.PayOption;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.KeyboardUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class PaymentActivity extends SubBaseActivity implements IPaymentView {
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;


    /*summery UI*/
    @BindView(R.id.layout_cart_summery)
    View layoutCartSummery;
    @BindView(R.id.tv_total_mrp)
    TextView tvTotallMrp;
    @BindView(R.id.tv_total_savings)
    TextView tvTotallSavings;
    @BindView(R.id.tv_total_pay)
    TextView tvTotallPay;
    @BindView(R.id.et_promocode)
    AppCompatEditText etPromocode;


    private String TAG = "PaymentActivity";
    private Context context;
    private String source = "";
    public static FetchCartSummaryResponse fetchCartSummaryResponse;
    ArrayList<PayOption> payOptionArrayList;
    ArrayList<CheckoutOrderData> orderDataList;
    private JsonArray jsonArray;
    CartSummaryData cartSummaryData;
    IPaymentPresenter presenter;
    private String enteredPromoCode;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.payment));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_payment, fragmentLayout);
        ButterKnife.bind(this);
        KeyboardUtils.hideSoftInput(this);
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();

    }

    @Override
    public void init() {
        context = PaymentActivity.this;
        presenter = new PaymentPresenter(this);
        //etPromocode.setOnEditorActionListener(this);
        callCheckSummery(null);
    }

    @Override
    public void callCheckSummery(String couponCode) {
        if (Validation.isValidString(CommonUtils.getAuthorizationKey(context))) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.fetchSummery(CommonUtils.getAuthorizationKey(context), couponCode);
        } else
            finish();
    }

    @Override
    public void setOrderSummeryRes(FetchCartSummaryResponse res) {
        if (Validation.isValidStatus(res)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            fetchCartSummaryResponse = res;
            setData();
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    private void setData() {
        if (source.equals(Constants.SOURCE_CONFIRM_ORDER)) {
            setPaymentMenthods();
        }

        if (fetchCartSummaryResponse.getCartSummaryData() != null) {
            cartSummaryData = fetchCartSummaryResponse.getCartSummaryData();
            setSummery();
        } else
            layoutCartSummery.setVisibility(View.GONE);
    }

    private void setPaymentMenthods() {

    }

    private void setSummery() {
        layoutCartSummery.setVisibility(View.VISIBLE);
        CommonUtils.setCartSummery(this, cartSummaryData, tvTotallMrp, tvTotallSavings, tvTotallPay);
    }

    @OnClick(R.id.view_COD)
    void clickOnCODE() {
        callCheckOutApi();
    }

    @OnClick(R.id.et_promocode)
    void onClickPromocode() {
        KeyboardUtils.showSoftInput(this);
    }


    @OnEditorAction(R.id.et_promocode)
    public boolean onEditorActionDone(int action) {
        if (action == EditorInfo.IME_ACTION_DONE) {
            callApplyPromocodeApi();
        }
        return false;
    }

    @OnClick(R.id.iv_apply_promocode)
    void onClickApplyPromocode() {
        callApplyPromocodeApi();
    }

    private void callApplyPromocodeApi() {
        KeyboardUtils.hideSoftInput(this);
        enteredPromoCode = etPromocode.getText().toString();
        if (Validation.isValidStrWithLength(enteredPromoCode, 4)) {
            if (Validation.isValidList(fetchCartSummaryResponse.getOrderSummeryArrayList())
                    && Validation.isValidString(fetchCartSummaryResponse.getOrderSummeryArrayList().get(0).getOrderId())) {
                showLoader(null);
                presenter.applyCoupons(enteredPromoCode, fetchCartSummaryResponse.getOrderSummeryArrayList().get(0).getOrderId(), CommonUtils.getAuthorizationKey(context));
            } else
                showMsg(getString(R.string.please_try_later));
        } else
            showMsg(getString(R.string.err_valid_coupon_code));
    }


    @Override
    public void setCheckoutRes(CheckoutArrayResponse response) {
        if (Validation.isValidStatus(response)) {
            if (Validation.isValidList(response.getOrderList())) {
                SharedPreferenceManger.saveCartData(context, null);
                CommonUtils.goToDashBoard(context);
            } else
                showMsg(getString(R.string.checkout_api_error));
        } else if (Validation.isValidStrMsg(response))
            showMsg(response.getMessage());
        else {
            if (Validation.isValidThrowableMsg(response)) {
                SharedPreferenceManger.saveCartData(context, null);
                showMsg(context.getString(R.string.order_placed_success));
                CommonUtils.goToDashBoard(context);
            } else {
                showMsg(getString(R.string.checkout_api_error));
                showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }
    }

    @Override
    public void setApplyCouponRes(ApplyCouponRes response) {
        if (Validation.isValidStatus(response)) {
            if (Validation.isValidString(response.getMessage()))
                showMsg(response.getMessage());
            callCheckSummery(response.getCoupon_code());
        } else if (Validation.isValidStrMsg(response))
            showMsg(response.getMessage());
        else
            showMsg(null);
    }


    @Override
    public void callCheckOutApi() {
        if (NetworkStatus.getInstance().isOnline2(context)) {
            prepareJsonObjects();
            showLoader(null);
            presenter.checkoutApi(CommonUtils.getCustomerId(context), jsonArray);
        } else
            showMsg(getString(R.string.error_no_internet));
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (msg != null) ? msg : context.getString(R.string.error_something_wrong), 0);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    private void prepareJsonObjects() {
        orderDataList = fetchCartSummaryResponse.getOrderSummeryArrayList();
        jsonArray = new JsonArray();
        for (int i = 0; i < orderDataList.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(Constants.ID_ORDER, orderDataList.get(i).getOrderId());
            jsonObject.addProperty(Constants.TOTAL_PAID, orderDataList.get(i).getGrandTotal());
            jsonObject.addProperty(Constants.TOTAL_PAID, orderDataList.get(i).getGrandTotal());
            jsonObject.addProperty(Constants.PAY_TYPE, Constants.PAY_TYPE_COD);
            jsonObject.addProperty(Constants.PAY_OPTION, "COD");
            jsonObject.addProperty(Constants.EXPRESS, 1);
            jsonObject.addProperty(Constants.DELIVERY_SLOT, orderDataList.get(i).getDelivery_slot());
            jsonArray.add(jsonObject);
        }
    }
}
