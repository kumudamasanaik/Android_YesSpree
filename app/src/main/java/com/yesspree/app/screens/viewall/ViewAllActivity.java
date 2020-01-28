package com.yesspree.app.screens.viewall;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.DashboardResModel;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ViewAllBrandsRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAllActivity extends SubBaseActivity implements IViewAllActivityView, IAdapterClickListener {
    private String TAG = "ViewAllActivity";

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;


    @BindView(R.id.rv_banner10)
    RecyclerView rvBanner10;

    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;

    @BindView(R.id.empty_button)
    TextView btnEmptyCart;

    HomeRecyclerAdapter rvBanner10Adapter;

    String source = "";
    private Context mContext;
    private ProductListInput input;
    ViewAllActivityPresenter viewAllActivityPresenter;
    DashboardResModel.DashboardData dashboardData;

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.shop_by_brands));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_all, fragmentLayout);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            input = getIntent().getParcelableExtra(Constants.FILTER_OBJ);
            init();
            //return;
        }

    }


    @Override
    public void init() {
        mContext = this;
        setSpecificScreenData();
        viewAllActivityPresenter = new ViewAllActivityPresenter(this);
        setBanner10();
        callViewAllBrandsApi();
    }


    @Override
    public void callViewAllBrandsApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        if (Validation.isValidObject(input))
            if (NetworkStatus.getInstance().isOnline2(mContext)) {
                viewAllActivityPresenter.callViewAllBrandsApi(input);
            } else {
                showMsg(getString(R.string.error_no_internet));
            }
    }

    @Override
    public void setViewAllBrandsApiResp(ViewAllBrandsRespModel data) {
        if (Validation.isValidStatus(data)) {
            if (Validation.isValidList(data.getBrandsArrayList())) {
                rvBanner10Adapter.addList(data.getBrandsArrayList());
                showViewState(MultiStateView.VIEW_STATE_CONTENT);
            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
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

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callViewAllBrandsApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callViewAllBrandsApi();
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && data instanceof Brands) {
            Brands brands = (Brands) data;
            CommonUtils.navigateBannerData(mContext, Constants.TYPE_BRAND, brands.getBrand_en());
        }
    }


    public void setBanner10() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvBanner10Adapter = new HomeRecyclerAdapter(mContext, R.layout.adapter_brands_view_all_list_item, this, Constants.TYPE_BRAND);
        rvBanner10.setLayoutManager(linearLayoutManager);
        rvBanner10.addItemDecoration(CommonUtils.getItemDecoration(rvBanner10.getContext(), linearLayoutManager));
        rvBanner10.setAdapter(rvBanner10Adapter);
    }
}
