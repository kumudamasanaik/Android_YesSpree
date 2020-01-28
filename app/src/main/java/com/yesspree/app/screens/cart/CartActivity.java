package com.yesspree.app.screens.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.modelapi.Advertisement;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.cofirmorder.ConfirmOrderActivity;
import com.yesspree.app.screens.explore.ExploreViewActvity;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends SubBaseActivity implements ICartActivityView, IProductListClickListener {
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.view_payment)
    LinearLayout viewPayment;
    @BindView(R.id.view_saving_price)
    LinearLayout viewSavingPrice;
    @BindView(R.id.view_original_price)
    LinearLayout viewOriginalPrice;
    @BindView(R.id.view_seperator)
    View viewSeperator;
    @BindView(R.id.recycler_product_list)
    RecyclerView recyclerProductList;
    @BindView(R.id.btn_checkout)
    Button btnCheckout;
    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;
    @BindView(R.id.empty_button)
    TextView btnEmptyCart;


    /*summery UI*/
    @BindView(R.id.layout_cart_summery)
    View partialExploreView;
    @BindView(R.id.tv_total_mrp)
    TextView tvTotallMrp;
    @BindView(R.id.tv_total_savings)
    TextView tvTotallSavings;
    @BindView(R.id.tv_total_pay)
    TextView tvTotallPay;

    /*Explore view*/
    @BindView(R.id.view_explore)
    View layout_explore_view;

    @BindView(R.id.ic_backgrond)
    AppCompatImageView backgroundImage;

    @BindView(R.id.tv_explore)
    TextView tv_explore;

    @BindView(R.id.ic_close)
    AppCompatImageView ic_explore_close;

    @BindView(R.id.tv_explore_query)
    TextView tv_explore_title;

    @BindView(R.id.scrooler_nestedScrollVIew)
    NestedScrollView scrooler_nestedScrollVIew;

    /*variables*/
    CartActivityPresenter presenter;
    private String source;
    private String TAG = "CartActivityView";
    private Context mContext;
    private CartListAdapter cartListAdapter;
    CartSummaryData cartSummaryData;
    private String cartOpType;
    private ArrayList<Advertisement> mExploreList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_cart, fragmentLayout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        init();


    }

    @Override
    public void init() {
        mContext = this;
        presenter = new CartActivityPresenter(this);
        setSpecificScreenData();
        setCartAdapter();
        callfetchCartApi();
    }

    @Override
    public void setSpecificScreenData() {

        title.setText(getString(R.string.my_cart));
        tvEpmtyCart.setText(mContext.getString(R.string.err_empty_cart));
        btnEmptyCart.setText(mContext.getString(R.string.add_some_prod_to_cart));
    }

    private void setCartAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        cartListAdapter = new CartListAdapter(mContext, this, recyclerProductList, Constants.CART_PRODUCTS);
        recyclerProductList.setLayoutManager(linearLayoutManager);
        recyclerProductList.addItemDecoration(CommonUtils.getItemDecoration(recyclerProductList.getContext(), linearLayoutManager));
        recyclerProductList.setAdapter(cartListAdapter);
        recyclerProductList.setNestedScrollingEnabled(false);
    }

    @Override
    public void callfetchCartApi() {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            if (CommonUtils.checkLoginStatus(mContext, mContext.getString(R.string.login_to_add_to_cart))) {
                if (Validation.isValidString(CommonUtils.getCustomerId(mContext)) && Validation.isValidString(CommonUtils.getSession(mContext))) {
                    showViewState(MultiStateView.VIEW_STATE_LOADING);
                    presenter.fetchCart(CommonUtils.getCustomerId(mContext), CommonUtils.getSession(mContext));
                } else
                    CommonUtils.doLogin(mContext, Constants.SOURCE_CART);
            }
        } else
            Toast.makeText(mContext, getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setFetchRes(FetchCartResponse object) {
        try {
            if (Validation.isValidStatus(object)) {
                if (Validation.isValidList(object.getProductList())) {
                    cartListAdapter.addList(object.getProductList());
                    showViewState(MultiStateView.VIEW_STATE_CONTENT);
                } else
                    showViewState(MultiStateView.VIEW_STATE_EMPTY);
                //Cart Summary
                if (object.getCartCartSummaryDataData() != null) {
                    SharedPreferenceManger.saveCartData(mContext, new Gson().toJson(object.getCartCartSummaryDataData()));
                    cartSummaryData = object.getCartCartSummaryDataData();
                    setCartSummeryView(cartSummaryData);
                } else
                    partialExploreView.setVisibility(View.GONE);

                /*Explore view*/
                if (Validation.isValidList(object.getAdvertisement())) {
                    mExploreList = object.getAdvertisement();
                    setupExploreView();
                } else
                    layout_explore_view.setVisibility(View.GONE);


            } else {
                showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    public void setCartSummeryView(CartSummaryData cartSummaryData) {
        if (recyclerProductList != null && recyclerProductList.getAdapter().getItemCount() > 0) {
            partialExploreView.setVisibility(View.VISIBLE);
            CommonUtils.setCartSummery(this, cartSummaryData, tvTotallMrp, tvTotallSavings, tvTotallPay);
        } else {
            partialExploreView.setVisibility(View.GONE);
            showViewState(MultiStateView.VIEW_STATE_EMPTY);
        }

    }

    @Override
    public void onClickProduct(Object productData, int pos, String opType, String specificProdType) {
        cartOpType = opType;
        if (opType.equals(Constants.PRODUCT_CART_MODIFY1) || opType.equals(Constants.PRODUCT_CART_MODIFY2)) {
            CartModifyParam cartModifyParam = (CartModifyParam) productData;
            showLoader(null);
            presenter.modifyCart(cartModifyParam);
        }

        if (opType.equals(Constants.CART_REMOVE)) {
            CartModifyParam cartModifyParam = (CartModifyParam) productData;
            showLoader(null);
            presenter.removeFromCart(cartModifyParam);
        }
    }


    @Override
    public void setModifyCart(FetchCartResponse fetchRes) {
        if (cartOpType.equals(Constants.PRODUCT_CART_MODIFY1)) {
            cartListAdapter.setModifyCart(fetchRes);
        }
    }

    @Override
    public void setRemoveFromCart(FetchCartResponse fetchRes) {
        if (cartOpType.equals(Constants.CART_REMOVE)) {
            cartListAdapter.setRemoveFromCart(fetchRes);
        }
    }

    @Override
    public void setExploreViewResp(ResponseModel responseModel) {
        if (responseModel == null)
            showMsg(null);
        if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getUrl())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SOURCE, Constants.OFFERS_DETAIL);
            bundle.putString(Constants.EXTRA_EXPLORE_URL, mExploreList.get(0).getUrl());
            CommonUtils.startActivity(mContext, ExploreViewActvity.class, bundle);
        }
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        finish();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callfetchCartApi();
    }

    @OnClick(R.id.btn_checkout)
    void onClickBtnCheckout() {
        if (Validation.isValidObject(recyclerProductList) && Validation.isValidObject(recyclerProductList.getAdapter())
                && recyclerProductList.getAdapter().getItemCount() > 0) {
            startOrderConfirmScreen();
        } else {
            CommonUtils.showToastMsg(mContext, getString(R.string.no_product_checkout), 0);
            finish();
        }
    }

    @OnClick(R.id.view_explore)
    void showWebViewForExplore() {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            showLoader(null);
            if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getId()))
                presenter.callExploreClickApi(mExploreList.get(0).getId());
        } else {
            showMsg(getString(R.string.error_no_internet));
        }
    }

    @OnClick(R.id.ic_close)
    void closeExploreView() {
        layout_explore_view.setVisibility(View.GONE);
    }

    private void startOrderConfirmScreen() {
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_CART);
        startActivity(intent);
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(mContext, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), false);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }


    private void setupExploreView() {
        layout_explore_view.setVisibility(View.VISIBLE);
        if (Validation.isValidString(mExploreList.get(0).getPic()))
            ImageUtils.setImage(backgroundImage, mExploreList.get(0).getPic());

        if (Validation.isValidString(mExploreList.get(0).getTitle()))
            tv_explore_title.setText(mExploreList.get(0).getTitle());

    }

}
