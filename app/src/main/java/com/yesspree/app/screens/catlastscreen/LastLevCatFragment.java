package com.yesspree.app.screens.catlastscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.customviews.snap.GravitySnapHelper;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.LastLevelCatRes;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.home.adapter.BannerApter;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.productlist.ProductMainMainListFragment;
import com.yesspree.app.screens.sub_sub_category.Sub_SubCategoryFragment;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LastLevCatFragment extends Fragment implements ILastLevCatView, IAdapterClickListener {


    /*view*/
    @BindView(R.id.main_banner_pager)
    ViewPager mainBannerViewPager;
    @BindView(R.id.main_banner_indicator)
    CirclePageIndicator mainBannerIndicator;
    @BindView(R.id.main_banner_pager_layout)
    LinearLayout mainBannerPagerLayout;
    @BindView(R.id.rv_cat)
    RecyclerView rvCat;
    @BindView(R.id.rv_popular_products)
    RecyclerView rv_popular_products;
    @BindView(R.id.rv_big_square_banner)
    RecyclerView rv_BigSquarBanner;
    @BindView(R.id.rv_fixed_square_banner4)
    RecyclerView rv_fixed_square_banner4;
    @BindView(R.id.rv_brands)
    RecyclerView rvBrands;
    @BindView(R.id.tv_view_all_brands)
    AppCompatTextView tvViewAllBrands;
    @BindView(R.id.layout_brands)
    LinearLayout layoutBrands;
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    Unbinder unbinder;


    /*variables*/
    private String mLastLevelCatId;
    private String mLastLevelCatNAme;
    private OnFragmentInteractionListener mListener;
    private Context context;
    ILastLevCatPresenter presenter;
    LastLevelCatRes res;
    BannerApter mainBannerAdapter;
    HomeRecyclerAdapter rvCateAdapter;
    HomeRecyclerAdapter rvbanner1Adapter;
    HomeRecyclerAdapter rvbanner2Adapter;
    HomeRecyclerAdapter rvbanner3Adapter;
    HomeRecyclerAdapter rvBrandsAdapter;

    ArrayList<Bannerdata> mainBannerdataArrayList;
    ArrayList<Category> categoryArrayList;
    ArrayList<Bannerdata> popularProductArrayList;
    ArrayList<Bannerdata> bigSquareBannerArrayList;
    ArrayList<Bannerdata> fixedSquarBanner_4ArrayList;
    ArrayList<Brands> brandsArrayList;
    MultipleCatRespModel.ChildCategory childCatModel;
    FragmentActivity mActivity;

    public LastLevCatFragment() {
        // Required empty public constructor
    }

    public static LastLevCatFragment newInstance() {
        LastLevCatFragment fragment = new LastLevCatFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLastLevelCatId = getArguments().getString(Constants.CATEGOPRY_ID);
            mLastLevelCatNAme = getArguments().getString(Constants.EXTRAS_CATEGORY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_lev_categ, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void init() {
        presenter = new LastLevCatPresenter(this);
        if (NetworkStatus.getInstance().isOnline2(context))
            callLastLevelCatApi();
        else {
            showMsg(getString(R.string.error_no_internet));
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void callLastLevelCatApi() {
        if (Validation.isValidString(mLastLevelCatId)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.callLastcatApi(mLastLevelCatId);
        }
    }

    @Override
    public void callChildCatagoryData(String childCatID) {
        if (Validation.isValidString(childCatID)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.callChildCataData(childCatID);
        }
    }

    @Override
    public void setLastLevelCatApiResponce(LastLevelCatRes res) {
        if (Validation.isValidStatus(res)) {
            this.res = res;
            setData();
        } else if (Validation.isValidStrMsg(res)) {
            showMsg(res.getMessage());
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void setChildCatApiResponce(MultipleCatRespModel res) {


        if (Validation.isValidStatus(res)) {
            this.childCatModel = res.getChildcat();
            navigateChildeCatData();

        } else if (Validation.isValidStrMsg(res)) {
            showMsg(res.getMessage());
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);


    }


    private void setData() {
        if (Validation.isValidObject(res.getResult())) {


            if (Validation.isValidObject((DashBoardActivity) mActivity) && Validation.isValidString(mLastLevelCatNAme))
                ((DashBoardActivity) mActivity).title.setText(mLastLevelCatNAme);

            if (Validation.isValidList(res.getResult().getCategory())) {
                categoryArrayList = res.getResult().getCategory();
                setcateList();
            }

            setupBannerData();

            if (Validation.isValidList(res.getResult().getBrands())) {
                layoutBrands.setVisibility(View.VISIBLE);
                brandsArrayList = res.getResult().getBrands();
                setBrands();
            } else
                layoutBrands.setVisibility(View.GONE);

            showViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else

            showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }


    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callLastLevelCatApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callLastLevelCatApi();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.context = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_view_all_brands})
    void navigateViewAll(View view) {
        switch (view.getId()) {
            case R.id.tv_view_all_brands:
                CommonUtils.navigateViewAll(context, Constants.TYPE_BRANDS, Constants.TYPE_BRANDS, null);
                break;
        }

    }


    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        if (Validation.isValidObject(data) && data instanceof Category) {
            Category category = ((Category) data);
            if (getActivity() != null && getActivity() instanceof DashBoardActivity) {
                if (Validation.isValidString(category.get_id()))
                    callChildCatagoryData(category.get_id());
            }
        }

        if (Validation.isValidObject(data) && data instanceof Bannerdata) {
            {
                Bannerdata bannerdata = (Bannerdata) data;
                CommonUtils.navigateBannerData(context, bannerdata.getType(), bannerdata.getTarget());
            }
        }
        if (Validation.isValidObject(data) && data instanceof Brands) {
            {
                Brands brands = (Brands) data;
                CommonUtils.navigateBannerData(context, Constants.TYPE_BRAND, brands.getBrand_en());
            }
        }
    }

    private void navigateChildeCatData() {

        if (Validation.isValidList(childCatModel.getCategory())) {
            // list havaing category data move into sub sub categoryView
            if (getActivity() != null && getActivity() instanceof DashBoardActivity) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRAS_SUB_CATEGORY_ID, mLastLevelCatId);
                bundle.putString(Constants.EXTRAS_CATEGORY_NAME, mLastLevelCatNAme);

                Sub_SubCategoryFragment subCatFragment = Sub_SubCategoryFragment.newInstance();
                subCatFragment.setArguments(bundle);
                //((DashBoardActivity) mActivity).bottomNavigationView.setSelectedItemId(R.id.action_category);
                CommonUtils.addFragment(subCatFragment, mActivity);

            }


        } else {
            // list dont have category data move into ProductListing
            if (getActivity() != null && getActivity() instanceof DashBoardActivity) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CATEGOPRY_ID, mLastLevelCatId);
                bundle.putString(Constants.EXTRAS_CATEGORY_NAME, mLastLevelCatNAme);
                ProductMainMainListFragment subCatFragment = ProductMainMainListFragment.newInstance();
                subCatFragment.setArguments(bundle);
                //((DashBoardActivity) mActivity).bottomNavigationView.setSelectedItemId(R.id.action_category);
                CommonUtils.addFragment(subCatFragment, mActivity);
            }
        }
    }

    private void setPagerBanner() {
        mainBannerViewPager.setClipToPadding(false);
        mainBannerAdapter = new BannerApter(context, R.layout.item_banner, this, Constants.TYPE_MAIN_CATEGORY);
        mainBannerViewPager.setAdapter(mainBannerAdapter);
        mainBannerIndicator.setViewPager(mainBannerViewPager);
        mainBannerAdapter.addArrayList(mainBannerdataArrayList);
    }

    private void setcateList() {

        rvCateAdapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image9, this, "");
        rvCat.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        rvCat.addItemDecoration(new InsetDecoration(context));
        rvCat.setAdapter(rvCateAdapter);
        rvCat.setNestedScrollingEnabled(false);
        rvCateAdapter.addList(categoryArrayList);

    }

    private void setupPopularProducts() {
        rvbanner1Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image3, this, Constants.TYPE_RV_BANNER_ADPTER);
        rv_popular_products.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        rv_popular_products.addItemDecoration(new InsetDecoration(context));
        rv_popular_products.setAdapter(rvbanner1Adapter);
        rv_popular_products.setNestedScrollingEnabled(false);

        if (popularProductArrayList.size() > 6) {
            popularProductArrayList.subList(6, popularProductArrayList.size()).clear();
            rvbanner1Adapter.addList(popularProductArrayList);
        } else {
            rvbanner1Adapter.addList(popularProductArrayList);
        }


    }

    private void setupBigSquareBanner() {
        LinearLayoutManager linearLayoutManager = CommonUtils.getRecyclerLinearLayoutManager(context);
        rvbanner2Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image2, this, Constants.TYPE_RV_BANNER_2ADPTER);
        rv_BigSquarBanner.setLayoutManager(linearLayoutManager);
        new GravitySnapHelper(Gravity.END).attachToRecyclerView(rv_BigSquarBanner);
        rv_BigSquarBanner.addItemDecoration(new InsetDecoration(context, R.dimen.dimens_4));
        rv_BigSquarBanner.setAdapter(rvbanner2Adapter);
        rvbanner2Adapter.addList(bigSquareBannerArrayList);
    }

    private void setupFixedSquareBanner() {
        GridLayoutManager gridLayoutManager = CommonUtils.getRecyclerGridLayoutManager(context, 2);
        rvbanner3Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image7, this, Constants.TYPE_RV_BANNER_3ADPTER);
        rv_fixed_square_banner4.setLayoutManager(gridLayoutManager);
        rv_fixed_square_banner4.addItemDecoration(new InsetDecoration(context, R.dimen.card_insets));
        rv_fixed_square_banner4.setAdapter(rvbanner3Adapter);
        rv_fixed_square_banner4.setNestedScrollingEnabled(false);
        if (fixedSquarBanner_4ArrayList.size() > 4) {
            fixedSquarBanner_4ArrayList.subList(4, fixedSquarBanner_4ArrayList.size()).clear();
            rvbanner3Adapter.addList(fixedSquarBanner_4ArrayList);
        } else {
            rvbanner3Adapter.addList(fixedSquarBanner_4ArrayList);
        }
    }

    private void setBrands() {
        rvBrandsAdapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image3, this, Constants.TYPE_BRAND);
        rvBrands.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        rvBrands.addItemDecoration(new InsetDecoration(context, R.dimen.card_insets));
        rvBrands.setAdapter(rvBrandsAdapter);
        rvBrands.setNestedScrollingEnabled(false);

        if (brandsArrayList.size() > 6) {
            brandsArrayList.subList(6, brandsArrayList.size()).clear();
            rvBrandsAdapter.addList(brandsArrayList);
        } else {
            rvBrandsAdapter.addList(brandsArrayList);
        }
    }


    private void setupBannerData() {

        if (Validation.isValidList(res.getResult().getBannerArrayList())) {
            try {
                if (Validation.isValidList(res.getResult().getBannerArrayList().get(0).getBannerdata())) {
                    mainBannerPagerLayout.setVisibility(View.VISIBLE);
                    mainBannerdataArrayList = res.getResult().getBannerArrayList().get(0).getBannerdata();
                    setPagerBanner();
                } else {
                    mainBannerPagerLayout.setBackgroundResource(R.drawable.dummy_product);
                }


                if (Validation.isValidList(res.getResult().getBannerArrayList().get(1).getBannerdata())) {
                    rv_BigSquarBanner.setVisibility(View.VISIBLE);
                    bigSquareBannerArrayList = res.getResult().getBannerArrayList().get(1).getBannerdata();
                    setupBigSquareBanner();
                } else rv_BigSquarBanner.setVisibility(View.GONE);


                if (Validation.isValidList(res.getResult().getBannerArrayList().get(2).getBannerdata())) {
                    rv_popular_products.setVisibility(View.VISIBLE);
                    popularProductArrayList = res.getResult().getBannerArrayList().get(2).getBannerdata();
                    setupPopularProducts();
                } else rv_popular_products.setVisibility(View.GONE);


                if (Validation.isValidList(res.getResult().getBannerArrayList().get(3).getBannerdata())) {
                    rv_fixed_square_banner4.setVisibility(View.VISIBLE);
                    fixedSquarBanner_4ArrayList = res.getResult().getBannerArrayList().get(3).getBannerdata();
                    setupFixedSquareBanner();
                } else rv_fixed_square_banner4.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
