package com.yesspree.app.screens.orderconfirmation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.MyLogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderConfirmationActivity extends SubBaseActivity implements IOrderConfirmationView {

    @BindView(R.id.tv_product_header)
    TextView tvProductHeader;
    @BindView(R.id.tv_more_product_detail)
    TextView tvMoreProductDetail;

    private String TAG = "MyOrderConfirmationActivity";
    private Context mContext;
    private String source;

    @BindView(R.id.view_payment)
    LinearLayout viewPayment;
    @BindView(R.id.view_saving_price)
    LinearLayout viewSavingPrice;
    @BindView(R.id.view_original_price)
    LinearLayout viewOriginalPrice;
    @BindView(R.id.view_seperator)
    View viewSeperator;

    @BindView(R.id.recycler_product_detail)
    RecyclerView recyclerProductDetail;
    @BindView(R.id.product_detail_control)
    RelativeLayout productDetailControl;
    @BindView(R.id.view_separator)
    View viewSeparator;
    @BindView(R.id.view_header_delivery_address)
    RelativeLayout viewHeaderDeliveryAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_Addressname)
    TextView tvAddressname;
    @BindView(R.id.tv_locality)
    TextView tvLocality;
    @BindView(R.id.view_address)
    LinearLayout viewAddress;
    @BindView(R.id.tv_DeliveryTimeHeader)
    TextView tvDeliveryTimeHeader;
    @BindView(R.id.view_delivery_time_header)
    LinearLayout viewDeliveryTimeHeader;
    @BindView(R.id.tv_delivery_date)
    TextView tvDeliveryDate;
    @BindView(R.id.tv_delivery_time)
    TextView tvDeliveryTime;
    @BindView(R.id.delievry_time_view)
    RelativeLayout delievryTimeView;
    @BindView(R.id.btn_checkout)
    Button btnCheckout;


    private HomeRecyclerAdapter mAddressListAdapter;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.order_confirmation));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_my_order_confirmation, fragmentLayout);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();

    }

    @Override
    public void init() {
        mContext = MyOrderConfirmationActivity.this;
        setConfirmedProductsData();

    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {

    }

    @Override
    public void hideLoader() {

    }

    @OnClick(R.id.tv_more_product_detail)
    public void onViewClicked() {

        if (recyclerProductDetail.getVisibility() == View.VISIBLE) {
            recyclerProductDetail.setVisibility(View.GONE);
            tvMoreProductDetail.setBackgroundResource(R.drawable.ic_expand_more);


        } else {
            recyclerProductDetail.setVisibility(View.VISIBLE);
            tvMoreProductDetail.setBackgroundResource(R.drawable.ic_expand_less);

        }

    }


    private void setConfirmedProductsData() {
        mAddressListAdapter = new HomeRecyclerAdapter(mContext, R.layout.partial_product_list_item);
        recyclerProductDetail.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerProductDetail.addItemDecoration(new InsetDecoration(mContext));
        recyclerProductDetail.setAdapter(mAddressListAdapter);
        recyclerProductDetail.setNestedScrollingEnabled(false);


    }
}
