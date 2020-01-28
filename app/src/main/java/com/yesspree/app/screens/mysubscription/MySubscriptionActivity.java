package com.yesspree.app.screens.mysubscription;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.screens.common.ViewPagerAdapter;
import com.yesspree.app.screens.mysubscription.active_fragment.SubscriptionActiveFragment;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.MyLogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yesspree.app.constatnts.Constants.ACTIVE;
import static com.yesspree.app.constatnts.Constants.CANCELLED;

public class MySubscriptionActivity extends SubBaseActivity implements IMySubscriptionView {

    private String TAG = "MySubscriptionActivity";
    private Context mContext;
    private ViewPagerAdapter mViewPagerAdapter;
    private String source;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.filter_view_pager)
    ViewPager filterViewPager;
    private ViewPager.OnPageChangeListener changeListener;

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.my_subscription));
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
        mContext = MySubscriptionActivity.this;
        setupTabLayout();

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

    @Override
    public void setupTabLayout() {
        setMainBannerView();

    }

    void setMainBannerView() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragments(SubscriptionActiveFragment.newInstance(ACTIVE), ACTIVE);
        mViewPagerAdapter.addFragments(SubscriptionActiveFragment.newInstance(CANCELLED), CANCELLED);
        filterViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(filterViewPager);


        changeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SubscriptionActiveFragment fragment = (SubscriptionActiveFragment) mViewPagerAdapter.getItem(position);
                fragment.callMySubscrApi();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        filterViewPager.addOnPageChangeListener(changeListener);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changeListener.onPageSelected(0);
            }
        }, 500);
    }

}
