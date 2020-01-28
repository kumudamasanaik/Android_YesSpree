package com.yesspree.app.screens.myorderstatus;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.modelapi.MyOrderStatusRes;
import com.yesspree.app.modelapi.OrderData;
import com.yesspree.app.modelapi.OrderTrackData;
import com.yesspree.app.modelapi.OrderedProduct;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.cofirmorder.ConfirmOrderProdAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;
import com.yesspree.app.utility.ViewAnimationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderStatusActivity extends SubBaseActivity implements IMyOrderStatusView {

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.view_order_detail)
    LinearLayout layoutOrderDetails;
    @BindView(R.id.recycler_product_detail)
    RecyclerView rvProducts;
    @BindView(R.id.tv_more_product_detail)
    ImageView ivExpCollapse;
    @BindView(R.id.tv_product_header)
    TextView tvProductDetils;
    @BindView(R.id.layout_status)
    LinearLayout layoutOrderStatus;


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
    @BindView(R.id.view_modify_address)
    View viewModifyAddress;
    @BindView(R.id.tv_delivery_address_label)
    TextView tvDeliveryAddLabel;

    /*summery UI*/
    @BindView(R.id.layout_cart_summery)
    View layoutCartSummery;
    @BindView(R.id.tv_total_mrp)
    TextView tvTotallMrp;
    @BindView(R.id.tv_total_savings)
    TextView tvTotallSavings;
    @BindView(R.id.tv_total_pay)
    TextView tvTotallPay;

    /*oder details*/
    @BindView(R.id.tv_placed_on)
    TextView tvOrderPlacedOn;

    @BindView(R.id.tv_order_id)
    TextView tvOrderId;


    /*veriables*/
    private Context context;
    MyOrderStatusPresenter presenter;
    private String source, orderNo;
    private LinearLayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private OrderProdAdapter adapter;
    private boolean isCollapsed = false;
    private ArrayList<OrderedProduct> productDataArrayList;
    private AddressData addressData;
    private CartSummaryData cartSummaryData;
    OrderData orderDetils;

    MyOrderStatusRes myOrderStatusRes;
    private ArrayList<OrderTrackData> trackArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_my_order_status, fragmentLayout);
        ButterKnife.bind(this);

        if (Validation.isValidObject(getIntent())) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            orderNo = getIntent().getStringExtra(Constants.ORDER_NUMBER);
            init();
        }
    }

    @Override
    public void init() {
        context = this;
        setSpecificScreenData();
        showViewState(MultiStateView.VIEW_STATE_EMPTY);
        presenter = new MyOrderStatusPresenter(this);
        rvProducts.setVisibility(View.GONE);
        viewModifyAddress.setVisibility(View.INVISIBLE);
        if (Validation.isValidString(orderNo))
            callMyOrderStatusApi();
        else
            finish();
    }

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.my_orders));
    }

    @Override
    public void callMyOrderStatusApi() {
        if (NetworkStatus.getInstance().isOnline2(context)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.getMyOrderStatus(orderNo);
        } else
            showMsg(getString(R.string.error_no_internet));
    }

    @Override
    public void setMyOrderRes(MyOrderStatusRes res) {
        if (Validation.isValidStatus(res)) {
            myOrderStatusRes = res;
            setData();
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    private void setData() {
        if (Validation.isValidObject(myOrderStatusRes.getOrderData())) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            orderDetils = myOrderStatusRes.getOrderData();

            cartSummaryData = new CartSummaryData();

            cartSummaryData.setMrp(orderDetils.getTotalMrp());
            cartSummaryData.setSelling_price(orderDetils.getTotalSellingPrice());
            cartSummaryData.setGrand_total(orderDetils.getGrand_total());
            setSummery();

            if (Validation.isValidList(myOrderStatusRes.getOrderTrackDataArrayList())) {
                trackArrayList = myOrderStatusRes.getOrderTrackDataArrayList();
                setTrackStatus();
            }
        } else {
            layoutCartSummery.setVisibility(View.GONE);
            layoutOrderDetails.setVisibility(View.GONE);
        }

        if (Validation.isValidList(myOrderStatusRes.getProductList())) {
            productDataArrayList = myOrderStatusRes.getProductList();
            setProdList();
        }

        if (Validation.isValidObject(myOrderStatusRes.getAddressData())) {
            addressData = myOrderStatusRes.getAddressData();
            setAddress();
        }
    }

    private void setTrackStatus() {
        Typeface typeface = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold);
        layoutOrderStatus.removeAllViews();
        for (int i = 0; i < trackArrayList.size(); i++) {
            View trackStatusItem = LayoutInflater.from(context).inflate(R.layout.item_track_status, null);
            OrderTrackData trackData = trackArrayList.get(i);
            TextView tvStatus = trackStatusItem.findViewById(R.id.tv_satus);
            ImageView ivStatus = trackStatusItem.findViewById(R.id.ic_status);
            View viewLine = trackStatusItem.findViewById(R.id.view_line);

            if (Validation.isValidString(trackData.getName()))
                tvStatus.setText(trackData.getName());

            if (Validation.isValidString(orderDetils.getOrderStatus()) && Validation.isValidString(trackData.getName())) {
                if (trackData.getName().equalsIgnoreCase(orderDetils.getOrderStatus())) {
                    ivStatus.setImageResource(R.drawable.ic_radio_button);
                    tvStatus.setTypeface(typeface);
                    tvStatus.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorPrimary, null));
                }
            }
            if (i == trackArrayList.size() - 1)
                viewLine.setVisibility(View.GONE);
            layoutOrderStatus.addView(trackStatusItem);
        }
    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callMyOrderStatusApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callMyOrderStatusApi();
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

    private void setProdList() {
        layoutManager = new LinearLayoutManager(context);
        dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_4dp, null));
        adapter = new OrderProdAdapter(context);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.addItemDecoration(dividerItemDecoration);
        rvProducts.setAdapter(adapter);
        rvProducts.setNestedScrollingEnabled(false);

        tvProductDetils.setText(getString(R.string.product_details, productDataArrayList.size()));
        adapter.addList(productDataArrayList);
    }

    private void setAddress() {
        tvDeliveryAddLabel.setText(getString(R.string.delivery_address));
        if (Validation.isValidString(addressData.getAddress_type()))
            tvAddressType.setText(addressData.getAddress_type());
        if (Validation.isValidString(addressData.getAddress_type()))
            tvAddresNamae.setText(addressData.getName());
        if (Validation.isValidString(addressData.getAddress1()))
            tvAddress.setText(addressData.getAddress1());
        else if (Validation.isValidString(addressData.getAddress1()))
            tvAddress.setText(addressData.getAddress2());
    }

    private void setSummery() {
        if (Validation.isValidString(orderDetils.getInvoiceNumber()))
            tvOrderId.setText(orderDetils.getInvoiceNumber());
        if (Validation.isValidString(orderDetils.getInvoiceDate()))
            tvOrderPlacedOn.setText(orderDetils.getInvoiceDate());

        layoutCartSummery.setVisibility(View.VISIBLE);
        CommonUtils.setCartSummery(this, cartSummaryData, tvTotallMrp, tvTotallSavings, tvTotallPay);

    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

}
