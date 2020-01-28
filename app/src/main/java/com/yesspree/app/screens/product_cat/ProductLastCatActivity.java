package com.yesspree.app.screens.product_cat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.viewpagerindicator.CirclePageIndicator;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.screens.home.adapter.BannerApter;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.MyLogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductLastCatActivity extends SubBaseActivity implements IProductLastCatActivityView {

    private String source;
    private Context mContext;
    private String TAG = "ProductLastCatActivity";
    private BannerApter mainBannerAdapter;
    private HomeRecyclerAdapter mRvCat_1Adapter;
    private HomeRecyclerAdapter mRvCat_2Adapter;
    private HomeRecyclerAdapter mRvCat_3Adapter;
    private HomeRecyclerAdapter mRvCat_4Adapter;
    private HomeRecyclerAdapter mShopByBrandAdapter;




    private HomeRecyclerAdapter mUserLikeProductAdapter;

    @BindView(R.id.main_banner_pager)
    ViewPager mainBannerViewPager;

    @BindView(R.id.main_banner_indicator)
    CirclePageIndicator mainBannerIndicator;


    @BindView(R.id.rv_cat1)
    RecyclerView rv_cat1;

    @BindView(R.id.rv_cat2)
    RecyclerView rv_cat2;

    @BindView(R.id.rv_main_cat3)
    RecyclerView rv_cat3;


    @BindView(R.id.rv_cat4)
    RecyclerView rv_cat4;


    @BindView(R.id.recyclerview_shopByBrand)
    RecyclerView recyclerview_shopByBrand;

    @Override
    public void setSpecificScreenData() {
        title.setText("product name");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product_last_cat, fragmentLayout);
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
        mContext = this;
        //  setMainBannerView();
        setupCatgory_1();
        setupCatgory_2();
        setupCatgory_3();
        setupCatgory_4();
        setupShopByBrands();

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

    void setMainBannerView() {
        mainBannerViewPager.setClipToPadding(false);
        mainBannerAdapter = new BannerApter(mContext, new ArrayList<Object>(), R.layout.partial_product_detail_swipe_imageview_list_item);
        mainBannerViewPager.setAdapter(mainBannerAdapter);
        mainBannerIndicator.setViewPager(mainBannerViewPager);
    }


    private void setupCatgory_1() {
        mRvCat_1Adapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image7);
        rv_cat1.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        rv_cat1.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_insets));
        rv_cat1.setAdapter(mRvCat_1Adapter);
        rv_cat1.setNestedScrollingEnabled(false);
    }


    private void setupCatgory_2() {

        mRvCat_2Adapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image2);
        rv_cat2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_cat2.addItemDecoration(new InsetDecoration(mContext));
        rv_cat2.setAdapter(mRvCat_2Adapter);

    }

    private void setupCatgory_3() {

        mRvCat_3Adapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image7);
        rv_cat3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_cat3.addItemDecoration(new InsetDecoration(mContext));
        rv_cat3.setAdapter(mRvCat_3Adapter);
    }



    private void setupCatgory_4() {

        mRvCat_4Adapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image7);
        rv_cat4.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        rv_cat4.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_insets));
        rv_cat4.setAdapter(mRvCat_4Adapter);
        rv_cat4.setNestedScrollingEnabled(false);

    }


    private void setupShopByBrands() {

        mShopByBrandAdapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image3);
        recyclerview_shopByBrand.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        recyclerview_shopByBrand.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_insets));
        recyclerview_shopByBrand.setAdapter(mShopByBrandAdapter);
    }




}
