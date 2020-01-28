package com.yesspree.app.screens.aboutus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.modelapi.AboutusRespModel;
import com.yesspree.app.modelapi.Query;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.aboutus.query.QueryFragment;
import com.yesspree.app.screens.common.ViewPagerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutusActivity extends SubBaseActivity implements IAboutusView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.filter_view_pager)
    ViewPager filterViewPager;
    private String TAG = "AboutusActivity";
    private Context mContext;
    private ViewPagerAdapter mViewPagerAdapter;
    private String source;
    private List<Query> mHeaderList;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    IAboutusPresenter iAboutusPresenter;
    AboutusRespModel.Data aboutData;

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.about_us));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_aboutus, fragmentLayout);
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
        iAboutusPresenter = new AboutusPresenter(this);
        mContext = AboutusActivity.this;
        callAboutusApi();
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(this, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(this, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    @Override
    public void callAboutusApi() {

        showViewState(MultiStateView.VIEW_STATE_LOADING);
        if (NetworkStatus.getInstance().isOnline2(this)) {
            iAboutusPresenter.callAboutusApi();

        } else {
            showMsg(getString(R.string.error_no_internet));
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void setAboutusApiResp(AboutusRespModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                aboutData = data.getAboutusData();
                setMainBannerView();
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }


    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callAboutusApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callAboutusApi();
    }

    void setMainBannerView() {

        if (Validation.isValidObject(aboutData)) {
            mHeaderList = new ArrayList<>();
            if (Validation.isValidList(aboutData.getAboutus())) {
                mHeaderList.add(aboutData.getAboutus().get(0));
            }
            if (Validation.isValidList(aboutData.getPrivacyPolicy())) {
                mHeaderList.add(aboutData.getPrivacyPolicy().get(0));
            }

            if (Validation.isValidList(aboutData.getTermsCondition())) {
                mHeaderList.add(aboutData.getTermsCondition().get(0));
            }

            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            if (Validation.isValidList(mHeaderList)) {
                for (Query cur : mHeaderList) {
                    mViewPagerAdapter.addFragments(QueryFragment.newInstance(), cur.getType());

                }
            }

            filterViewPager.setAdapter(mViewPagerAdapter);
            tabLayout.setupWithViewPager(filterViewPager);


            filterViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    setupTabLayoutData(position);
                }

                @Override
                public void onPageSelected(int position) {
                    setupTabLayoutData(position);
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }
    private void setupTabLayoutData(int position) {
        QueryFragment queryFragment = (QueryFragment) mViewPagerAdapter.getItem(position);
        if (queryFragment != null) {
            if (Validation.isValidList(mHeaderList))
                queryFragment.setupData(mHeaderList.get(position));
        }
    }
}
