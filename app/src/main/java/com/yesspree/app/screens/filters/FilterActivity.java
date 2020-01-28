package com.yesspree.app.screens.filters;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.modelapi.FilterTypeRes;
import com.yesspree.app.modelapi.FilterValue;
import com.yesspree.app.modelapi.Refine;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.common.ViewPagerAdapter;
import com.yesspree.app.screens.filters.refined_fragment.RefinedFragment;
import com.yesspree.app.screens.filters.sort_fragment.SortByFragment;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yesspree.app.constatnts.Constants.REFINE_BY;
import static com.yesspree.app.constatnts.Constants.SORTED_BY;

public class FilterActivity extends SubBaseActivity implements IFilterView {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.filter_view_pager)
    ViewPager filterViewPager;
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    /*variables*/
    private String TAG = "FilterActivity";
    private Context context;
    private ViewPagerAdapter mViewPagerAdapter;
    private String source;
    FilterPresenter presenter;
    private String catId = "";
    FilterTypeRes filterTypeRes;
    public ArrayList<Refine> refineArrayList;
    public ArrayList<FilterValue> sortArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_filter, fragmentLayout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            catId = getIntent().getStringExtra(Constants.CATEGOPRY_ID);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();
    }

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.filter));
    }


    @Override
    public void init() {
        context = FilterActivity.this;
        presenter = new FilterPresenter(this);
        showViewState(MultiStateView.VIEW_STATE_EMPTY);
        //setupTabLayout();
        getFilterType();
    }

    @Override
    public void getFilterType() {
        if (NetworkStatus.getInstance().isOnline2(context)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.callFilterType(catId);
        } else
            showMsg(getString(R.string.error_no_internet));
    }

    @Override
    public void setFilterTypeRes(FilterTypeRes res) {
        if (Validation.isValidStatus(res)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            filterTypeRes = res;
            refineArrayList = filterTypeRes.getRefineArrayList();
            sortArrayList = filterTypeRes.getSortArrayList();
            setupTabLayout();
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

    @Override
    public void setupTabLayout() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragments(RefinedFragment.newInstance(), REFINE_BY);
        mViewPagerAdapter.addFragments(SortByFragment.newInstance(), SORTED_BY);
        filterViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(filterViewPager);
    }
}
