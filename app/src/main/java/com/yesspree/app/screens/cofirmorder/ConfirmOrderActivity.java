package com.yesspree.app.screens.cofirmorder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.modelapi.CheckoutOrderData;
import com.yesspree.app.modelapi.DeliverySlotData;
import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.addaddress.AddAddressActivity;
import com.yesspree.app.screens.address_selection.AddressSelectionActivity;
import com.yesspree.app.screens.payment.PaymentActivity;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;
import com.yesspree.app.utility.ViewAnimationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrderActivity extends SubBaseActivity implements IConfirmOrderView {

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.recycler_product_detail)
    RecyclerView rvProducts;
    @BindView(R.id.tv_more_product_detail)
    ImageView ivExpCollapse;
    @BindView(R.id.tv_product_header)
    TextView tvProductDetils;
    /*summery UI*/
    @BindView(R.id.layout_cart_summery)
    View layoutCartSummery;
    @BindView(R.id.tv_total_mrp)
    TextView tvTotallMrp;
    @BindView(R.id.tv_total_savings)
    TextView tvTotallSavings;
    @BindView(R.id.tv_total_pay)
    TextView tvTotallPay;

    /*address*/
    @BindView(R.id.tv_addressType)
    TextView tvAddressType;
    @BindView(R.id.tv_name)
    TextView tvAddresNamae;
    @BindView(R.id.tv_Addressname)
    TextView tvAddress;
    @BindView(R.id.tv_locality)
    TextView tvLocality;
    @BindView(R.id.tv_city_name)
    TextView tvCity;
    @BindView(R.id.tv_delivery_address_label)
    TextView tv_delivery_address_label;
    /*delivery slot*/
    @BindView(R.id.tv_delivery_date)
    TextView tvDeliveryDate;
    @BindView(R.id.tv_delivery_time)
    TextView tvDeliveryTime;


    /*variables*/
    Context context;
    IConfirmOrderPresenter presenter;
    String TAG = "ConfirmOrderActivityView";
    FetchCartSummaryResponse summeryRes;
    CartSummaryData cartSummaryData;
    ArrayList<ProductData> productDataArrayList;
    AddressData addressData;
    String source;
    private Intent intent;
    ConfirmOrderProdAdapter adapter;
    LinearLayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private ArrayList<DeliverySlotData> deliverySlotList;
    private JsonArray jsonArray;
    private ArrayList<CheckoutOrderData> orderDataList;
    private boolean isCollapsed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_my_order_confirmation, fragmentLayout);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            init();
        }
    }


    @Override
    public void init() {
        context = ConfirmOrderActivity.this;
        presenter = new ConfirmOrderPresenter(this);
        setSpecificScreenData();
        rvProducts.setVisibility(View.GONE);
        showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.order_confirmation));
    }


    @OnClick(R.id.delievry_time_view)
    void onClikcDeliverySlot() {
        if (Validation.isValidList(deliverySlotList)) {
            showDeliverySlots();
        }
    }

    @OnClick({R.id.product_detail_control})
    void clickForExpandCollapseProd(View view) {
        if (Validation.isValidObject(rvProducts.getAdapter()) && rvProducts.getAdapter().getItemCount() > 0) {
            isCollapsed = !isCollapsed;
            if (isCollapsed) {
                ViewAnimationUtils.expand(rvProducts);
                ivExpCollapse.animate().rotationBy(-180).start();
            } else {
                ViewAnimationUtils.collapse(rvProducts);
                ivExpCollapse.animate().rotationBy(180).start();
            }
        }
    }

    @OnClick(R.id.view_modify_address)
    void clikcOnModifyAddress() {

        startModifyAddressScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchOrderSummeryApi();
    }

    @Override
    public void fetchOrderSummeryApi() {
        if (NetworkStatus.getInstance().isOnline2(context)) {
            if (Validation.isValidObject(CommonUtils.getSession(context))) {
                if (Validation.isValidObject(CommonUtils.getPincode(context))) {
                    if (Validation.isValidObject(CommonUtils.getAuthorizationKey(context))) {
                        showViewState(MultiStateView.VIEW_STATE_LOADING);
                        presenter.fetchOrderSummery(CommonUtils.getSession(context), CommonUtils.getPincode(context), CommonUtils.getAuthorizationKey(context));
                    } else
                        MyLogUtils.e(TAG, getString(R.string.err_key));
                } else
                    MyLogUtils.e(TAG, getString(R.string.err_pincode));
            } else
                MyLogUtils.e(TAG, getString(R.string.err_session));
        } else
            CommonUtils.showToastMsg(context, getString(R.string.error_no_internet), 0);
    }

    @Override
    public void setOrderSummeryRes(FetchCartSummaryResponse res) {
        if (Validation.isValidStatus(res)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            summeryRes = res;
            setData();
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    private void setData() {
        if (Validation.isValidObject(summeryRes.getCartSummaryData())) {
            cartSummaryData = summeryRes.getCartSummaryData();
            setSummery();
        } else
            layoutCartSummery.setVisibility(View.VISIBLE);

        if (Validation.isValidList(summeryRes.getProductDataArrayList())) {
            productDataArrayList = summeryRes.getProductDataArrayList();
            setProdList();
        }

        if (Validation.isValidObject(summeryRes.getAddress())) {
            addressData = summeryRes.getAddress();
            setAddress();
        } else
            startAddAddressScreen();

        if (Validation.isValidList(summeryRes.getOrderSummeryArrayList()))
            orderDataList = summeryRes.getOrderSummeryArrayList();
        if (Validation.isValidList(orderDataList.get(0).getDeliverySlotList())) {
            deliverySlotList = orderDataList.get(0).getDeliverySlotList();
            if (Validation.isValidList(deliverySlotList.get(0).getTimes()))
                setDeliverySlotData(deliverySlotList.get(0).getDate(), deliverySlotList.get(0).getTimes().get(0).getTime());
        }


    }

    private void setDeliverySlotData(String date, String time) {
        tvDeliveryDate.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_delivery_date_time), null, null, null);
        if (Validation.isValidString(date) && Validation.isValidString(time)) {
            tvDeliveryDate.setText(date);
            tvDeliveryTime.setText(time);
        }
    }

    void showDeliverySlots() {
        final Dialog myDialog = new Dialog(context, R.style.CustomDialogThemeLightBg);
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.setContentView(R.layout.select_delivery_slot_dialog);
        LinearLayout dynamicLayout = myDialog.findViewById(R.id.dynamic_layout);
        dynamicLayout.removeAllViews();

        for (int i = 0; i < deliverySlotList.size(); i++) {
            View childLayoutHeader = LayoutInflater.from(context).inflate(R.layout.item_delivery_slot, null);
            TextView deliverySlotHeader = (TextView) childLayoutHeader.findViewById(R.id.delivery_slot);
            deliverySlotHeader.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
            deliverySlotHeader.setTextColor(ContextCompat.getColor(context, R.color.app_text_black));
            deliverySlotHeader.setText(deliverySlotList.get(i).getDate());
            deliverySlotHeader.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            //CommonUtils.setLayoutFont(context, "SegoePro-Semibold.ttf", deliverySlotHeader);
            dynamicLayout.addView(childLayoutHeader);
            for (int j = 0; j < deliverySlotList.get(i).getTimes().size(); j++) {
                View childLayout = LayoutInflater.from(context).inflate(R.layout.item_delivery_slot, null);
                TextView deliverySlot = (TextView) childLayout.findViewById(R.id.delivery_slot);
                deliverySlot.setText(deliverySlotList.get(i).getTimes().get(j).getTime());
                deliverySlot.setTag(deliverySlotList.get(i).getDate() + "&&&" + deliverySlotList.get(i).getTimes().get(j).getTime());
                if (i == deliverySlotList.size() - 1 && j == deliverySlotList.get(i).getTimes().size() - 1) {
                    childLayout.findViewById(R.id.delivery_slot_divider).setVisibility(View.GONE);
                }
                deliverySlot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() != null) {
                            String tag = v.getTag().toString();
                            setDeliverySlotData(tag.split("&&&")[0], tag.split("&&&")[1]);
                        }
                        myDialog.dismiss();
                    }
                });
                dynamicLayout.addView(childLayout);
            }
        }
        myDialog.findViewById(R.id.dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();

    }


    private void setAddress() {
        if (Validation.isValidString(addressData.getAddress_type()))
            tvAddressType.setText(addressData.getAddress_type());
        if (Validation.isValidString(addressData.getAddress_type()))
            tvAddresNamae.setText(addressData.getName());
        if (Validation.isValidString(addressData.getAddress1()))
            tvAddress.setText(addressData.getAddress1());
        else if (Validation.isValidString(addressData.getAddress1()))
            tvAddress.setText(addressData.getAddress2());
        tv_delivery_address_label.setText(getString(R.string.delivery_address));
    }

    private void setProdList() {
        layoutManager = new LinearLayoutManager(context);
        dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_4dp, null));
        adapter = new ConfirmOrderProdAdapter(context);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.addItemDecoration(dividerItemDecoration);
        rvProducts.setAdapter(adapter);
        rvProducts.setNestedScrollingEnabled(false);

        tvProductDetils.setText(getString(R.string.product_details, productDataArrayList.size()));
        adapter.addList(productDataArrayList);
    }

    private void setSummery() {
        layoutCartSummery.setVisibility(View.VISIBLE);
        if (Validation.isStrIsInt(cartSummaryData.getMrp())) {
            layoutCartSummery.setVisibility(View.VISIBLE);

            CommonUtils.setCartSummery(this,cartSummaryData,tvTotallMrp,tvTotallSavings,tvTotallPay);
        }
    }


    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        fetchOrderSummeryApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        fetchOrderSummeryApi();
    }

    private void prepareJsonObjects() {
        jsonArray = new JsonArray();
        for (int i = 0; i < orderDataList.size(); i++) {
            orderDataList.get(i).setDelivery_slot(tvDeliveryDate.getText().toString() + ", " + tvDeliveryTime.getText().toString());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(Constants.ID_ORDER, orderDataList.get(i).getOrderId());
            jsonObject.addProperty(Constants.TOTAL_PAID, orderDataList.get(i).getGrandTotal());
            jsonObject.addProperty(Constants.PAY_TYPE, Constants.PAY_TYPE_COD);
            jsonObject.addProperty(Constants.PAY_OPTION, "COD");
            jsonObject.addProperty(Constants.EXPRESS, 1);
            jsonObject.addProperty(Constants.DELIVERY_SLOT, orderDataList.get(i).getDelivery_slot());
            jsonArray.add(jsonObject);
        }
    }

    @OnClick(R.id.btn_checkout)
    void clickOnCheckout() {
        if (Validation.isValidList(productDataArrayList)) {
            prepareJsonObjects();
            intent = new Intent(context, PaymentActivity.class);
            intent.putExtra(Constants.SOURCE, Constants.SOURCE_CONFIRM_ORDER);
            startActivity(intent);
        } else
            CommonUtils.showToastMsg(context, getString(R.string.no_product_checkout), 0);
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (msg != null) ? msg : context.getString(R.string.error_something_wrong), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    private void startAddAddressScreen() {
        intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_CONFIRM_ORDER);
        startActivityForResult(intent, Constants.CONFIRM_ORDER_CODE);
    }

    private void startModifyAddressScreen() {
        intent = new Intent(this, AddressSelectionActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_CONFIRM_ORDER);
        startActivityForResult(intent, Constants.CONFIRM_ORDER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CONFIRM_ORDER_CODE && resultCode == RESULT_OK) {
            if (data != null)
                fetchOrderSummeryApi();
            else {
                CommonUtils.showToastMsg(context, getString(R.string.no_address_add_address), 0);
                finish();
            }
        }
    }
}
