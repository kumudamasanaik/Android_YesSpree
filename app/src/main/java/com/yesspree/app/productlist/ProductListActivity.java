package com.yesspree.app.productlist;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.screens.productlist.productlist_fragment.ProductListFragment;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductListActivity extends SubBaseActivity implements IProductListView, OnFragmentInteractionListener {


    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    /*variables*/
    String source = "";
    ProductListInput input;
    String catId = "";
    private String TAG = "ProductListActivity";
    private Context context;
    ProductListPresenter presenter;
    int commitFrag = -1;
    ProductListFragment productListFragment;
    ProductMainListingRespModel response;
    String toolbarTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_prouct_list, fragmentLayout);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            context = ProductListActivity.this;
            source = getIntent().getStringExtra(Constants.SOURCE);
            input = getIntent().getParcelableExtra(Constants.FILTER_OBJ);
            toolbarTitle = getIntent().getStringExtra(Constants.EXTRA_PRODUCT_LIST_TITLE);
            if (savedInstanceState == null)
                addFragment();
            init();
            return;
        }
        finish();
    }

    public void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, ProductListFragment.newInstance(), Constants.SOURCE_PRODUCT_LIST_FRAG)
                .commit();
    }

    @Override
    public void init() {
        setSpecificScreenData();
        showViewState(MultiStateView.VIEW_STATE_CONTENT);
        /*presenter = new ViewAllBrandsFragmentPresenter(this);
        getProductList();*/

    }

    @Override
    public void onFragmentInteraction(Object data) {
        productListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentByTag(Constants.SOURCE_PRODUCT_LIST_FRAG);
        if (Validation.isValidObject(productListFragment))
            productListFragment.setProductListInput(Constants.SOURCE_PRODUCT_LIST_ACT, input);
    }

    @Override
    public void setSpecificScreenData() {
        if (Validation.isValidString(toolbarTitle)) {
            title.setText(toolbarTitle);
        } else
            title.setText(getString(R.string.products));

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        getProductList();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        getProductList();
    }

    @Override
    public void getProductList() {
        if (source.equals(Constants.SOURCE_FILTER)) {
            if (Validation.isValidObject(input)) {
                showViewState(MultiStateView.VIEW_STATE_LOADING);
                presenter.callFilterResult(input);
            }
        }
    }

    @Override
    public void setProductListRes(ProductMainListingRespModel res) {
        if (Validation.isValidStatus(res)) {
            response = res;
            setData();
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    private void setData() {
        if (Validation.isValidList(response.getProductDataArrayList())) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            /*if (productListFragment != null) {
                productListFragment.showViewState(MultiStateView.VIEW_STATE_CONTENT);
                productListFragment.setProductList(response.getProductDataArrayList());
            }*/
        } else
            showViewState(MultiStateView.VIEW_STATE_EMPTY);
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
