package com.yesspree.app.screens.myorder;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.screens.common.ViewPagerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends SubBaseActivity implements OnFragmentInteractionListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager orderViewPager;

    /*veriables*/
    private ViewPagerAdapter mViewPagerAdapter;
    ArrayList<String> orederTypeTileList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_my_order, fragmentLayout);
        ButterKnife.bind(this);
        setSpecificScreenData();
        setUpViewpager();
    }


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.my_orders));
    }

    private void setUpViewpager() {
        prepareFragList();
        orderViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(orderViewPager);
        orderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onFragmentInteraction(Object data) {

    }

    public void prepareFragList() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        orederTypeTileList = new ArrayList<>();

        orederTypeTileList.add(Constants.PRESENT);
        orederTypeTileList.add(Constants.DELIVERED);
        orederTypeTileList.add(Constants.CANCELLED);

        for (String type : orederTypeTileList) {
            mViewPagerAdapter.addFragments(OrdersListFragment.newInstance(type), type);
        }
    }
}
