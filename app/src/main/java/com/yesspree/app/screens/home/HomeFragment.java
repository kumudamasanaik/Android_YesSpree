package com.yesspree.app.screens.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.customviews.snap.GravitySnapHelper;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.Advertisement;
import com.yesspree.app.modelapi.Banner;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.DashboardResModel;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SpecificProducts;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.common.ProductListAdapter;
import com.yesspree.app.screens.explore.ExploreViewActvity;
import com.yesspree.app.screens.home.adapter.BannerApter;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.screens.subcat.SubCatFragment;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.TimerTask;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.yesspree.app.constatnts.Constants.BANNER_ROTATION_TIMER_7SECOND;

public class HomeFragment extends Fragment implements IHomeView, IAdapterClickListener, IProductListClickListener {

    //view
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.rv_main_cat)
    RecyclerView rvMainCat;
    @BindView(R.id.main_banner_pager)
    ViewPager mainBannerViewPager;
    @BindView(R.id.main_banner_indicator)
    CirclePageIndicator mainBannerIndicator;
    @BindView(R.id.rv_fixed_squarebanner)
    RecyclerView rvfixedsquareBanner1;
    @BindView(R.id.rv_big_square_banner)
    RecyclerView rvBigSquareBanner2;
    @BindView(R.id.rv_banner3)
    RecyclerView rvPopularProductsBanner3;
    @BindView(R.id.layout_specific_prod_type1)
    View layoutSpecificType1;
    @BindView(R.id.layout_specific_prod_type2)
    View layoutSpecificType2;
    @BindView(R.id.tv_specific_prod1)
    AppCompatTextView tvSpecificProd1;
    @BindView(R.id.tv_specific_prod2)
    AppCompatTextView tvSpecificProd2;
    @BindView(R.id.rv_banner4)
    RecyclerView rvBanner4;
/*    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;*/


    @BindView(R.id.vp_banner5)
    ViewPager vpRectangulerBanner5;

    // ViewPager vpRectangulerBanner5;

    @BindView(R.id.rv_banner6)
    RecyclerView rvSlidingBanner6;
    @BindView(R.id.rv_banner7)
    RecyclerView rvSquareFixed_4Banner7;
    @BindView(R.id.rv_banner8)
    RecyclerView rvOfferFixed_3Banner8;

    @BindView(R.id.rv_banner9)

    RecyclerView rvBanner9;
    @BindView(R.id.rv_banner10)
    RecyclerView rvBrands;
    @BindView(R.id.tv_banner_10_view_all)
    TextView tvBanner10ViewAll;
    @BindView(R.id.layout_banner_10)
    LinearLayout layoutBanner10;

    @BindView(R.id.rv_rectangular_banner)
    RecyclerView rv_rectangular_banner;


    /*sigle banner*/
    @BindView(R.id.item_banner_image8)
    View singleBannerLayout;
    @BindView(R.id.iv_prod)
    AppCompatImageView signleBannerIvProd;
    @BindView(R.id.tv_prod_name)
    AppCompatTextView signleTvProdName;
    Bannerdata singleBanner;

    /*Explore view*/
    @BindView(R.id.view_explore)
    View layout_explore_view;

    @BindView(R.id.ic_backgrond)
    AppCompatImageView backgroundImage;

    @BindView(R.id.tv_explore)
    TextView tv_explore;

    @BindView(R.id.ic_explore_close)
    AppCompatImageView ic_explore_close;


    @BindView(R.id.tv_explore_query)
    TextView tv_explore_title;

    //variables
    HomePresenter homePresenter;
    private OnFragmentInteractionListener mListener;
    Context context;
    FragmentActivity mActivity;
    private String TAG = "HomeFragment";
    View view;
    Unbinder unbinder;
    HomeRecyclerAdapter mainCatAdapter;
    HomeRecyclerAdapter rvfixedsquareBanner1Adapter;
    HomeRecyclerAdapter rvBigSquareBanner2Adapter;
    HomeRecyclerAdapter rvpopularProductsBanner3Adapter;
    ProductListAdapter rvBanner4Adapter;
    HomeRecyclerAdapter rvSlidingBanner6Adapter;
    HomeRecyclerAdapter rvSquareFixed_4Banner7Adapter;
    HomeRecyclerAdapter rvOfferFixed_3Banner8Adapter;
    ProductListAdapter rvBanner9Adapter;
    HomeRecyclerAdapter rvBrandsAdapter;
    BannerApter mainBannerAdapter;
    BannerApter rectangulerBannerAdapter5;

    DashboardResModel.DashboardData dashboardData;
    ArrayList<Category> categoryArrayList;
    ArrayList<SpecificProducts> specificProdMainArrayList;
    ArrayList<ProductData> specificProdList1;
    ArrayList<ProductData> specificProdList2;
    String specificProdType;
    ArrayList<Brands> brandsArrayList;
    ArrayList<Banner> allBannerData;
    ArrayList<Bannerdata> mainBannerArrayList1;
    ArrayList<Bannerdata> fixedsquareBannerArrayList2;
    ArrayList<Bannerdata> bigSquareBannerArrayList3;
    ArrayList<Bannerdata> popularProductsBannerdataArrayList4;
    ArrayList<Bannerdata> bannerdataArrayList5;
    ArrayList<Bannerdata> slidingBannerdataArrayList6;
    ArrayList<Bannerdata> squareFixed_4BannerdataArrayList7;
    ArrayList<Bannerdata> offerFixed_3BannerdataArrayList8;
    ArrayList<Bannerdata> rectangulerBannerdataArrayList5;
    ArrayList<Bannerdata> bannerdataArrayList10;
    ProductData mWishlIstProductData = null;
    DashboardResModel dashboardResModel;
    ArrayList<Advertisement> mExploreList;
    DividerItemDecoration dividerItemDecoration;

    private Handler mainBannerHandler;
    private Handler rectanguler_banner_Handler;
    private runnable rectanguler_banner_runnable;
    private Runnable mainBannerRunnable;

    private Handler top_bigSquareBannerHandler;
    private Runnable top_bigSquareBanner_Runnable;

    private Handler botom_bigSquar_banner_Handler;
    private runnable botom_bigSquar_banner_runnable;


    private int mainBannerCurrentPage = 0;
    private int rec_banner_currentPage = 0;

    private int topBigSquarBannerCurrentPage = 0;
    private int botomBig_banner_currentPage = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    @Override
    public void init() {
        homePresenter = new HomePresenter(this);
        callDashboardApi();
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        if (Validation.isValidObject(data) && data instanceof Category) {
            if (Validation.isValidString(opType) && opType.equalsIgnoreCase(Constants.TYPE_BANNER_MAIN_CAT_ADAPTER)) {
                if (getActivity() != null && getActivity() instanceof DashBoardActivity) {
                    if (Validation.isValidList(categoryArrayList)) {
                        Category selected = categoryArrayList.get(pos);

                        for (Category category : categoryArrayList)
                            if (category.isSelectedCategory()) category.setSelectedCategory(false);
                        ((Category) data).setSelectedCategory(true);

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(Constants.EXTRAS_CATEGORY_LIST, categoryArrayList);
                        bundle.putInt(Constants.SEL_POS, pos);
                        bundle.putString(Constants.EXTRAS_CATEGORY_ID, selected.get_id());
                        bundle.putString(Constants.EXTRAS_CATEGORY_NAME, selected.getName());
                        SubCatFragment subCatFragment = SubCatFragment.newInstance();

                        ((DashBoardActivity) mActivity).bottomNavigationView.setTag(new Object());
                        if (((DashBoardActivity) mActivity).bottomNavigationView.getSelectedItemId() != R.id.action_category)
                            ((DashBoardActivity) mActivity).bottomNavigationView.setSelectedItemId(R.id.action_category);

                        subCatFragment.setArguments(bundle);
                        ((DashBoardActivity) mActivity).addFragment(subCatFragment);
                    }
                }
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

    @Override
    public void onClickProduct(Object data, int pos, String opType, String adapterType) {
        specificProdType = adapterType;
        if (opType.equals(Constants.PRODUCT_DETAILS) && data instanceof ProductData) {
            ProductData productData = (ProductData) data;
            Intent move = new Intent(context, ProductDetailActivity.class);
            move.putExtra(Constants.EXTRA_PRODUCT_ID, productData.get_id());
            startActivity(move);
        }
        if (opType.equals(Constants.PRODUCT_CART_MODIFY1) || opType.equals(Constants.PRODUCT_CART_MODIFY2)) {
            CartModifyParam cartModifyParam = (CartModifyParam) data;
            showLoader(null);
            homePresenter.modifyCart(cartModifyParam);
        }
        if (opType.equals(Constants.PRODUCT_WISH_LIST_CHANGED) && data instanceof ProductData) {
            if (context != null) {
                mWishlIstProductData = (ProductData) data;
                showLoader(null);
                homePresenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(context), CommonUtils.getSession(context));
            }
        }
    }

    @Override
    public void setCartRes(FetchCartResponse cartRes) {
        //rvBanner4Adapter ,rvBanner9Adapter
        if (cartRes == null)
            showMsg(null);
        if (Validation.isValidString(specificProdType)) {
            if (specificProdType.equals(Constants.SPECIFIC_PRODUCT1))
                rvBanner4Adapter.setModifyCart(cartRes);
            if (specificProdType.equals(Constants.SPECIFIC_PRODUCT2))
                rvBanner9Adapter.setModifyCart(cartRes);
        }
    }

    @Override
    public void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse) {
        if (modifyWishlistResponse == null)
            showMsg(null);

        if (Validation.isValidString(specificProdType)) {
            if (specificProdType.equals(Constants.SPECIFIC_PRODUCT1))
                rvBanner4Adapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);
            if (specificProdType.equals(Constants.SPECIFIC_PRODUCT2))
                rvBanner9Adapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);
        }


    }

    @Override
    public void setExploreViewResp(ResponseModel responseModel) {
        if (responseModel == null)
            showMsg(null);
        if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getUrl())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SOURCE, Constants.HOME_FRAGMENT);
            bundle.putString(Constants.EXTRA_EXPLORE_URL, mExploreList.get(0).getUrl());
            CommonUtils.startActivity(context, ExploreViewActvity.class, bundle);
        }
    }

    @Override
    public void callDashboardApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        if (NetworkStatus.getInstance().isOnline2(context)) {
            homePresenter.callDashboardApi(context);

        } else {
            showMsg(getString(R.string.error_no_internet));
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void setDashboardApiRes(DashboardResModel data) {
        if (multiStateView != null) {
            if (Validation.isValidStatus(data)) {
                if (Validation.isValidObject(data.getDashboardData())) {
                    ((DashBoardActivity) mActivity).dashboardData = data.getDashboardData();
                    dashboardData = data.getDashboardData();
                    ((DashBoardActivity) mActivity).dashboardResModel = data;
                    if (Validation.isValidObject(data.getCartSummaryData()))
                        SharedPreferenceManger.saveCartData(context, new Gson().toJson(data.getCartSummaryData()));
                    setData();
                    if (Validation.isValidList(data.getAdvertisement())) {
                        mExploreList = data.getAdvertisement();
                        setupExploreView();
                    } else
                        layout_explore_view.setVisibility(View.GONE);

                    showViewState(MultiStateView.VIEW_STATE_CONTENT);
                } else
                    showViewState(MultiStateView.VIEW_STATE_ERROR);
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), true);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = requireContext();
        this.mActivity = requireActivity();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            MyLogUtils.e(TAG, "onAttach");
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
        destroyTheHandler();
        unbinder.unbind();
    }

    private void destroyTheHandler() {

        if (mainBannerHandler != null && mainBannerRunnable != null)
            mainBannerHandler.removeCallbacks(mainBannerRunnable);
        mainBannerRunnable = null;

        if (rectanguler_banner_Handler != null && rectanguler_banner_runnable != null)
            rectanguler_banner_Handler.removeCallbacks(rectanguler_banner_runnable);
        rectanguler_banner_runnable = null;


        if (top_bigSquareBannerHandler != null && top_bigSquareBanner_Runnable != null)
            top_bigSquareBannerHandler.removeCallbacks(top_bigSquareBanner_Runnable);
        top_bigSquareBanner_Runnable = null;


        if (botom_bigSquar_banner_Handler != null && botom_bigSquar_banner_runnable != null)
            botom_bigSquar_banner_Handler.removeCallbacks(botom_bigSquar_banner_runnable);
        botom_bigSquar_banner_runnable = null;

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callDashboardApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callDashboardApi();
    }

    @OnClick(R.id.item_banner_image8)
    void singelBannerViewClicked() {
        if (Validation.isValidObject(singleBanner)) {
            CommonUtils.navigateBannerData(context, singleBanner.getType(), singleBanner.getTarget());
        }
    }

    @OnClick(R.id.view_explore)
    void showWebViewForExplore() {
        if (NetworkStatus.getInstance().isOnline2(context)) {
            showLoader(null);
            if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getId()))
                homePresenter.callExploreClickApi(mExploreList.get(0).getId());
        } else {
            showMsg(getString(R.string.error_no_internet));
        }
    }

    @OnClick(R.id.ic_explore_close)
    void closeExploreView() {
        layout_explore_view.setVisibility(View.GONE);
    }


    @OnClick({R.id.tv_recommedend_products2, R.id.tv_recommedend_products1, R.id.tv_banner_10_view_all})
    void navigateViewAll(View view) {
        switch (view.getId()) {
            case R.id.tv_recommedend_products1:
                if (Validation.isValidString(specificProdMainArrayList.get(0).getTitle()))
                    CommonUtils.navigateViewAll(context, Constants.TYPE_SPECIFIC_PRODUCT_1, specificProdMainArrayList.get(0).getTitle(), null);
                break;
            case R.id.tv_recommedend_products2:
                if (Validation.isValidString(specificProdMainArrayList.get(1).getTitle()))
                    CommonUtils.navigateViewAll(context, Constants.TYPE_SPECIFIC_PRODUCT_2, specificProdMainArrayList.get(1).getTitle(), null);
                break;
            case R.id.tv_banner_10_view_all:
                CommonUtils.navigateViewAll(context, Constants.TYPE_BRANDS, Constants.TYPE_BRANDS, null);
                break;
        }


    }


    private void setData() {
        if (Validation.isValidObject((DashBoardActivity) mActivity))
            setAddress();

        if (Validation.isValidList(dashboardData.getCategory())) {
            categoryArrayList = dashboardData.getCategory();
            setMainCatView();
        }
        if (Validation.isValidList(dashboardData.getSpecificProdList())) {
            specificProdMainArrayList = dashboardData.getSpecificProdList();
            if (specificProdMainArrayList.size() >= 2) {


                specificProdList1 = specificProdMainArrayList.get(0).getProductDataArrayList();
                specificProdList2 = specificProdMainArrayList.get(0).getProductDataArrayList();

                if (Validation.isValidString(specificProdMainArrayList.get(0).getTitle()))
                    tvSpecificProd1.setText(specificProdMainArrayList.get(0).getTitle());

                if (Validation.isValidString(specificProdMainArrayList.get(1).getTitle()))
                    tvSpecificProd2.setText(specificProdMainArrayList.get(1).getTitle());

                if (Validation.isValidList(specificProdList1) && specificProdList1.size() > 4) {
                    layoutSpecificType1.setVisibility(View.VISIBLE);
                    specificProdList1.subList(4, specificProdList1.size()).clear();
                } //else layoutSpecificType1.setVisibility(View.GONE);

                if (Validation.isValidList(specificProdList1) && specificProdList2.size() > 4) {
                    layoutSpecificType2.setVisibility(View.VISIBLE);
                    specificProdList2.subList(4, specificProdList2.size()).clear();
                } //else layoutSpecificType2.setVisibility(View.GONE);

                if (specificProdList1.size() > 0) {
                    layoutSpecificType1.setVisibility(View.VISIBLE);
                    setBanner4();
                } else layoutSpecificType1.setVisibility(View.GONE);

                if (specificProdList1.size() > 0) {
                    layoutSpecificType2.setVisibility(View.VISIBLE);
                    setBanner9();
                } else layoutSpecificType2.setVisibility(View.GONE);


            }
        } else {
            layoutSpecificType1.setVisibility(View.GONE);
            layoutSpecificType2.setVisibility(View.GONE);
        }

        if (Validation.isValidList(dashboardData.getBrands())) {
            brandsArrayList = dashboardData.getBrands();
            setBrandsData();
        }

        if (Validation.isValidList(dashboardData.getBanner())) {
            allBannerData = dashboardData.getBanner();
            assignBannerList();
        }
    }


    private void assignBannerList() {
        switch (allBannerData.size()) {

            case 10:
                setAllBannerData();
                break;
            default: {
                if (allBannerData.size() >= 8) {
                    setAllBannerData();
                }
            }
        }
    }

    private void setAllBannerData() {
        if (Validation.isValidList(allBannerData.get(0).getBannerdata())) {
            mainBannerArrayList1 = allBannerData.get(0).getBannerdata();
            setMainBannerView();
        }

        if (Validation.isValidList(allBannerData.get(1).getBannerdata())) {
            fixedsquareBannerArrayList2 = allBannerData.get(1).getBannerdata();
            setFixedSquareBanner1();
        }
        if (Validation.isValidList(allBannerData.get(2).getBannerdata())) {
            bigSquareBannerArrayList3 = allBannerData.get(2).getBannerdata();
            setBigSquareBanner2();
        }

        if (Validation.isValidList(allBannerData.get(3).getBannerdata())) {
            popularProductsBannerdataArrayList4 = allBannerData.get(3).getBannerdata();
            setPopularProductsBanner3();
        }

        if (Validation.isValidList(allBannerData.get(4).getBannerdata())) {
            rectangulerBannerdataArrayList5 = allBannerData.get(4).getBannerdata();
            // rectangulerBannerSetup();
            setRectangulerBanner5();
        }

        if (Validation.isValidList(allBannerData.get(5).getBannerdata())) {
            slidingBannerdataArrayList6 = allBannerData.get(5).getBannerdata();
            setSlidingBanner6();
        }

        if (Validation.isValidList(allBannerData.get(6).getBannerdata())) {
            squareFixed_4BannerdataArrayList7 = allBannerData.get(6).getBannerdata();
            setSquareFixed_4Banner7();
        }

        if (Validation.isValidList(allBannerData.get(7).getBannerdata())) {
            offerFixed_3BannerdataArrayList8 = allBannerData.get(7).getBannerdata();
            setOfferFixed_3Banner8();
            setupSingleBannerView();
        } else {
            rvOfferFixed_3Banner8.setVisibility(View.GONE);
            singleBannerLayout.setVisibility(View.GONE);
        }

    }

    private void setupSingleBannerView() {
        if (Validation.isValidList(offerFixed_3BannerdataArrayList8)) {

            switch (offerFixed_3BannerdataArrayList8.size()) {
                case 1:
                case 2:
                    rvOfferFixed_3Banner8Adapter.addList(offerFixed_3BannerdataArrayList8);
                    break;
                default:
                    if (Validation.isValidList(offerFixed_3BannerdataArrayList8) && offerFixed_3BannerdataArrayList8.size() > 2) {
                        singleBanner = offerFixed_3BannerdataArrayList8.get(2);
                        if (Validation.isValidString(singleBanner.getTitle()))
                            signleTvProdName.setText(singleBanner.getTitle().toUpperCase());
                        if (Validation.isValidString(singleBanner.getPic()))
                            ImageUtils.setImageWitDefPic(context, signleBannerIvProd, singleBanner.getPic());
                        rvOfferFixed_3Banner8Adapter.addList(new ArrayList<>(offerFixed_3BannerdataArrayList8.subList(0, 2)));
                    }
                    break;
            }

        }

    }

    private void setMainCatView() {
        if (view != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            mainCatAdapter = new HomeRecyclerAdapter(mActivity, R.layout.item_main_category, this, Constants.TYPE_BANNER_MAIN_CAT_ADAPTER);
            rvMainCat.setLayoutManager(linearLayoutManager);
            rvMainCat.addItemDecoration(new InsetDecoration(mActivity));
            rvMainCat.setAdapter(mainCatAdapter);
            mainCatAdapter.addList(categoryArrayList);
        }
    }

    void setMainBannerView() {

        mainBannerViewPager.setClipToPadding(false);
        mainBannerAdapter = new BannerApter(context, R.layout.item_banner, this, Constants.TYPE_MAIN_CATEGORY);
        mainBannerViewPager.setAdapter(mainBannerAdapter);
        mainBannerIndicator.setViewPager(mainBannerViewPager);
        mainBannerAdapter.addArrayList(mainBannerArrayList1);
        // Auto start of viewpager
        mainBannerHandler = new Handler();
        new Timer().schedule(new TimerTask(mainBannerHandler, new runnable()), BANNER_ROTATION_TIMER_7SECOND, BANNER_ROTATION_TIMER_7SECOND);

        mainBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainBannerCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setFixedSquareBanner1() {
        GridLayoutManager gridLayoutManager = CommonUtils.getRecyclerGridLayoutManager(context, 2);
        rvfixedsquareBanner1Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image1, this, Constants.TYPE_RV_BANNER_ADPTER);
        rvfixedsquareBanner1.setLayoutManager(gridLayoutManager);
        rvfixedsquareBanner1.addItemDecoration(new InsetDecoration(context));
        rvfixedsquareBanner1.setAdapter(rvfixedsquareBanner1Adapter);
//        /* if data more than we need to show only  two data */
        if (fixedsquareBannerArrayList2.size() > 2) {
            fixedsquareBannerArrayList2.subList(2, fixedsquareBannerArrayList2.size()).clear();
            rvfixedsquareBanner1Adapter.addList(fixedsquareBannerArrayList2);
        } else {
            rvfixedsquareBanner1Adapter.addList(fixedsquareBannerArrayList2);
        }

    }

    private void setBigSquareBanner2() {
        LinearLayoutManager linearLayoutManager = CommonUtils.getRecyclerLinearLayoutManager(context);
        rvBigSquareBanner2Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image2, this, Constants.TYPE_RV_BANNER_2ADPTER);
        rvBigSquareBanner2.setLayoutManager(linearLayoutManager);
        new GravitySnapHelper(Gravity.END).attachToRecyclerView(rvBigSquareBanner2);
        rvBigSquareBanner2.addItemDecoration(new InsetDecoration(context, R.dimen.dimens_4));
        rvBigSquareBanner2.setAdapter(rvBigSquareBanner2Adapter);
        rvBigSquareBanner2Adapter.addList(bigSquareBannerArrayList3);
    }

    private void setPopularProductsBanner3() {
        rvpopularProductsBanner3Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image3, this, Constants.TYPE_RV_BANNER_3ADPTER);
        rvPopularProductsBanner3.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        rvPopularProductsBanner3.addItemDecoration(new InsetDecoration(context));
        rvPopularProductsBanner3.setAdapter(rvpopularProductsBanner3Adapter);
        rvPopularProductsBanner3.setNestedScrollingEnabled(false);

        if (popularProductsBannerdataArrayList4.size() > 6) {
            popularProductsBannerdataArrayList4.subList(6, popularProductsBannerdataArrayList4.size()).clear();
            rvpopularProductsBanner3Adapter.addList(popularProductsBannerdataArrayList4);
        } else {
            rvpopularProductsBanner3Adapter.addList(popularProductsBannerdataArrayList4);
        }
    }

    private void setBanner4() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
      /*  dividerItemDecoration = new DividerItemDecoration(rvBanner4.getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_2dp, null));*/
        rvBanner4Adapter = new ProductListAdapter(context, this, rvBanner4, Constants.SPECIFIC_PRODUCT1);
        rvBanner4.setLayoutManager(linearLayoutManager);
        rvBanner4.addItemDecoration(CommonUtils.getItemDecoration(rvBanner4.getContext(), linearLayoutManager));
        rvBanner4.setAdapter(rvBanner4Adapter);
        rvBanner4.setNestedScrollingEnabled(false);
        rvBanner4Adapter.addList(specificProdList1);
    }

    private void setRectangulerBanner5() {

        rectangulerBannerAdapter5 = new BannerApter(context, R.layout.item_banner_image5, this, Constants.BANNER_TYPE_VP);
        rectangulerBannerAdapter5.addArrayList(rectangulerBannerdataArrayList5);
        vpRectangulerBanner5.setAdapter(rectangulerBannerAdapter5);
        vpRectangulerBanner5.setOffscreenPageLimit(rectangulerBannerAdapter5.getCount());
        vpRectangulerBanner5.setClipChildren(false);
        vpRectangulerBanner5.setPageMargin(context.getResources().getDimensionPixelSize(R.dimen.dimens_10));


        rectanguler_banner_Handler = new Handler();
        new Timer().schedule(new TimerTask(rectanguler_banner_Handler, new runnable()), 2000, 2000);

        vpRectangulerBanner5.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rec_banner_currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void rectangulerBannerSetup() {

        LinearLayoutManager linearLayoutManager = CommonUtils.getRecyclerLinearLayoutManager(context);
        HomeRecyclerAdapter rectangulerAdpter = new HomeRecyclerAdapter(context, R.layout.item_banner_image5, this, Constants.BANNER_TYPE_VP);
        rv_rectangular_banner.setLayoutManager(linearLayoutManager);
        new GravitySnapHelper(Gravity.END).attachToRecyclerView(rv_rectangular_banner);
        rv_rectangular_banner.addItemDecoration(new InsetDecoration(context));
        rv_rectangular_banner.setAdapter(rectangulerAdpter);
        rectangulerAdpter.addList(rectangulerBannerdataArrayList5);
    }

    private void setSlidingBanner6() {
        LinearLayoutManager linearLayoutManager = CommonUtils.getRecyclerLinearLayoutManager(context);
        rvSlidingBanner6Adapter = new HomeRecyclerAdapter(context, R.layout.big_square_sliding_banner_adapter_list_item, this, Constants.TYPE_RV_BANNER_6ADPTER);
        rvSlidingBanner6.setLayoutManager(linearLayoutManager);
        new GravitySnapHelper(Gravity.END).attachToRecyclerView(rvSlidingBanner6);
        rvSlidingBanner6.addItemDecoration(new InsetDecoration(context, R.dimen.dimens_4));
        rvSlidingBanner6.setAdapter(rvSlidingBanner6Adapter);
        rvSlidingBanner6Adapter.addList(slidingBannerdataArrayList6);
    }

    private void setSquareFixed_4Banner7() {
        GridLayoutManager gridLayoutManager = CommonUtils.getRecyclerGridLayoutManager(context, 2);
        rvSquareFixed_4Banner7Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image7, this, Constants.TYPE_RV_BANNER_7);
        rvSquareFixed_4Banner7.setLayoutManager(gridLayoutManager);
        rvSquareFixed_4Banner7.addItemDecoration(new InsetDecoration(context, R.dimen.card_insets));
        rvSquareFixed_4Banner7.setAdapter(rvSquareFixed_4Banner7Adapter);
        rvSquareFixed_4Banner7.setNestedScrollingEnabled(false);
        if (squareFixed_4BannerdataArrayList7.size() > 4) {
            squareFixed_4BannerdataArrayList7.subList(4, squareFixed_4BannerdataArrayList7.size()).clear();
            rvSquareFixed_4Banner7Adapter.addList(squareFixed_4BannerdataArrayList7);
        } else {
            rvSquareFixed_4Banner7Adapter.addList(squareFixed_4BannerdataArrayList7);
        }

    }


    private void setOfferFixed_3Banner8() {
        rvOfferFixed_3Banner8Adapter = new HomeRecyclerAdapter(context, R.layout.item_banner_image8, this, Constants.TYPE_RV_BANNER_8);
        rvOfferFixed_3Banner8.setLayoutManager(new LinearLayoutManager(context));
        rvOfferFixed_3Banner8.addItemDecoration(new InsetDecoration(context));
        rvOfferFixed_3Banner8.setAdapter(rvOfferFixed_3Banner8Adapter);
        rvOfferFixed_3Banner8.setNestedScrollingEnabled(false);
        //rvOfferFixed_3Banner8Adapter.addList(offerFixed_3BannerdataArrayList8);

    }

    private void setBanner9() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        //   dividerItemDecoration = new DividerItemDecoration(rvBanner4.getContext(), linearLayoutManager.getOrientation());
        // dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_2dp, null));
        rvBanner9Adapter = new ProductListAdapter(context, this, rvBanner9, Constants.SPECIFIC_PRODUCT2);
        rvBanner9.setLayoutManager(linearLayoutManager);
        rvBanner9.addItemDecoration(CommonUtils.getItemDecoration(rvBanner9.getContext(), linearLayoutManager));
        rvBanner9.setAdapter(rvBanner9Adapter);
        rvBanner9.setNestedScrollingEnabled(false);
        rvBanner9Adapter.addList(specificProdList2);
    }

    private void setBrandsData() {
        GridLayoutManager gridLayoutManager = CommonUtils.getRecyclerGridLayoutManager(context, 3);
        rvBrandsAdapter = new HomeRecyclerAdapter(context, R.layout.partial_brand_list_item, this, Constants.TYPE_BANNER_ADPTER_10);
        rvBrands.setLayoutManager(gridLayoutManager);
        rvBrands.addItemDecoration(new InsetDecoration(context, R.dimen.card_insets));
        rvBrands.setAdapter(rvBrandsAdapter);
        if (brandsArrayList.size() > 6) {
            brandsArrayList.subList(6, brandsArrayList.size()).clear();
            rvBrandsAdapter.addList(brandsArrayList);
        } else {
            rvBrandsAdapter.addList(brandsArrayList);
        }

    }


    private void setupExploreView() {
        layout_explore_view.setVisibility(View.VISIBLE);
        if (Validation.isValidString(mExploreList.get(0).getPic()))
            ImageUtils.setImage(backgroundImage, mExploreList.get(0).getPic());

        if (Validation.isValidString(mExploreList.get(0).getTitle()))
            tv_explore_title.setText(mExploreList.get(0).getTitle());

    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction(null);
        }
    }

    private class runnable implements Runnable {
        @Override
        public void run() {

            if (Validation.isValidObject(mainBannerViewPager)) {
                if (mainBannerCurrentPage == mainBannerAdapter.getCount()) {
                    mainBannerCurrentPage = 0;
                }
                mainBannerViewPager.setCurrentItem(mainBannerCurrentPage++, true);
            }

            if (Validation.isValidObject(vpRectangulerBanner5)) {
                if (rec_banner_currentPage == rectangulerBannerAdapter5.getCount()) {
                    rec_banner_currentPage = 0;
                }
                vpRectangulerBanner5.setCurrentItem(rec_banner_currentPage++, true);
            }


        }
    }

    private void setAddress() {

        if (Validation.isValidObject((DashBoardActivity) mActivity)) {
            ((DashBoardActivity) mActivity).title.setText(getString(R.string.no_address));

            ((DashBoardActivity) mActivity).title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.dimens_10sp));
            ((DashBoardActivity) mActivity).tvCityName.setText(getString(R.string.no_city));
            if (Validation.isValidObject(SharedPreferenceManger.getAddressData(mActivity))) {
                AddressData addressData = SharedPreferenceManger.getAddressData(mActivity);
                if (Validation.isValidString(addressData.getAddress1()))
                    ((DashBoardActivity) mActivity).title.setText(addressData.getAddress1());
                if (Validation.isValidString(addressData.getCity())) {
                    ((DashBoardActivity) mActivity).tvCityName.setText(addressData.getCity());
                    ((DashBoardActivity) mActivity).tvCityName.setVisibility(View.VISIBLE);
                }
                if (Validation.isValidString(addressData.getPincode()))
                    CommonUtils.setPincode(context, addressData.getPincode());
            }
        }
    }

}
