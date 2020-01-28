package com.yesspree.app.screens.sub_sub_category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.customviews.MyViewPager;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.SubSubCatResponseModel;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.common.ProductListAdapter;
import com.yesspree.app.screens.common.ViewPagerAdapter;
import com.yesspree.app.screens.home.adapter.BannerApter;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.screens.sub_sub_category.category_fragments.MultipleCatFragment;
import com.yesspree.app.screens.subcat.adapter.SubCatCommonRecyclerAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Sub_SubCategoryFragment extends Fragment implements ISub_SubCategoryView, IAdapterClickListener, IProductListClickListener {
    private String TAG = "Sub_SubCategoryFragment";
    private OnFragmentInteractionListener mListener;
    Context mContext;
    View view;
    Unbinder unbinder;
    private String source;
    private BannerApter mainBannerAdapter;
    private HomeRecyclerAdapter mBanner2Adapter;

    private SubCatCommonRecyclerAdapter mBanner3Adapter;
    private HomeRecyclerAdapter mShopByBrandAdapter;


    private ProductListAdapter mTopProductAdapter;

    Sub_SubCategoryPresenter Sub_SubCategoryPresenter;

    @BindView(R.id.main_banner_pager)
    ViewPager mainBannerViewPager;

    @BindView(R.id.main_banner_indicator)
    CirclePageIndicator mainBannerIndicator;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;


    @BindView(R.id.rv_cat1)
    RecyclerView rv_Banner2;

    @BindView(R.id.rv_top_products)
    RecyclerView rv_top_products;


    @BindView(R.id.rv_sub_cat2)
    RecyclerView rv_banner3;


    @BindView(R.id.recyclerview_shopByBrand)
    RecyclerView recyclerview_shopByBrand;

    @BindView(R.id.view_top_products)
    View view_top_products;

    @BindView(R.id.layout_shopByBrands)
    View layout_shopByBrands;


    private ViewPagerAdapter mViewPagerAdapter;
    SubSubCatResponseModel.SubSubCatData dashboardData;


    @BindView(R.id.filter_view_pager)
    MyViewPager mSubCatViewPager;


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    String mCatId = "";
    String catName = "";
    FragmentActivity mActivity;
    String specificProdType;

    ArrayList<Category> categoryArrayList;
    ArrayList<String> categorynNameArrayList;
    ProductData mWishlIstProductData = null;
    Boolean first = true;
    public Sub_SubCategoryFragment() {
        // Required empty public constructor
    }

    public static Sub_SubCategoryFragment newInstance() {
        Sub_SubCategoryFragment fragment = new Sub_SubCategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Validation.isValidObject(getArguments())) {
            // categoryArrayList = getArguments().getParcelableArrayList(Constants.EXTRAS_CATEGORY_LIST);
            mCatId = getArguments().getString(Constants.EXTRAS_SUB_CATEGORY_ID);
            catName = getArguments().getString(Constants.EXTRAS_CATEGORY_NAME);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sub_subcategory, container, false);
        unbinder = ButterKnife.bind(this, view);


        init();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = getActivity();

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    @Override
    public void init() {
        Sub_SubCategoryPresenter = new Sub_SubCategoryPresenter(this);
        setupBanner2();
        setupBanner3();
        setupTopProducts();
        setupShopByBrands();
        callSubSubCatApi();


    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    public void callSubSubCatApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        Sub_SubCategoryPresenter.callSubSubCategory(mCatId);

    }

    @Override
    public void setCategoryApiRes(SubSubCatResponseModel data) {


        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data.getSubCatData())) {
                dashboardData = data.getSubCatData();
                setData();
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void setCartRes(FetchCartResponse cartRes) {

        if (cartRes == null)
            showMsg(null);
        if (Validation.isValidString(specificProdType)) {
            if (specificProdType.equals(Constants.TOP_PRODUCTS))
                mTopProductAdapter.setModifyCart(cartRes);

        }


    }

    @Override
    public void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse) {
        if (modifyWishlistResponse == null)
            showMsg(null);

        if (Validation.isValidString(specificProdType)) {
            if (specificProdType.equals(Constants.TOP_PRODUCTS))
                mTopProductAdapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);

        }

    }
    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }
    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callSubSubCatApi();
    }
    @OnClick(R.id.error_button)
    void clickErrorView() {
        callSubSubCatApi();
    }

    @OnClick({R.id.tv_top_products_view_all, R.id.tv_banner_10_view_all})
    void navigateViewAll(View view) {
        switch (view.getId()) {
            case R.id.tv_top_products_view_all:
                CommonUtils.navigateViewAll(mContext, Constants.TYPE_TOP_PRODUCTS, Constants.TYPE_TOP_PRODUCTS, mCatId);
                break;
            case R.id.tv_banner_10_view_all:
                CommonUtils.navigateViewAll(mContext, Constants.TYPE_BRANDS, Constants.TYPE_BRANDS, mCatId);
                break;
        }

    }


    private void setupViewPager() {

        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        if (Validation.isValidList(categorynNameArrayList)) {
            for (String productName : categorynNameArrayList) {
                mViewPagerAdapter.addFragments(MultipleCatFragment.newInstance(), productName);
            }
        }
        mSubCatViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(mSubCatViewPager);
        mSubCatViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (first && positionOffset == 0 && positionOffsetPixels == 0) {
                    onPageSelected(0);
                    first = false;
                }
            }
            @Override
            public void onPageSelected(int position) {
                updateMultiCatFragmentData(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setData() {
        if (Validation.isValidObject((DashBoardActivity) mActivity) && Validation.isValidString(catName))
            ((DashBoardActivity) mActivity).title.setText(catName);

        if (Validation.isValidList(dashboardData.getSub_sub_cat_headers())) {
            tabLayout.setVisibility(View.VISIBLE);
            mSubCatViewPager.setVisibility(View.VISIBLE);
            categoryArrayList = dashboardData.getSub_sub_cat_headers();
            categorynNameArrayList = new ArrayList<>();
            for (int i = 0; i < categoryArrayList.size(); i++) {
                if (Validation.isValidString(categoryArrayList.get(i).getName()))
                    categorynNameArrayList.add(categoryArrayList.get(i).getName());
            }
            setupViewPager();
        } else {
            mSubCatViewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
        }

        assignBannerArrayList();


        if (Validation.isValidList(dashboardData.getBrandsArrayList())) {
            mShopByBrandAdapter.addList(dashboardData.getBrandsArrayList());
            layout_shopByBrands.setVisibility(View.VISIBLE);
            ArrayList<Brands> brandsArrayList=dashboardData.getBrandsArrayList();
            if (Validation.isValidList(brandsArrayList) &&  brandsArrayList.size()> 6) {
                brandsArrayList.subList(6, brandsArrayList.size()).clear();
                mShopByBrandAdapter.addList(dashboardData.getBrandsArrayList());
            } else {
                mShopByBrandAdapter.addList(dashboardData.getBrandsArrayList());
            }

        } else {
            layout_shopByBrands.setVisibility(View.GONE);
        }
        if (Validation.isValidList(dashboardData.getTopProductList())) {
            mTopProductAdapter.addList(dashboardData.getTopProductList());
            view_top_products.setVisibility(View.VISIBLE);

        } else {
            view_top_products.setVisibility(View.GONE);
        }
    }

    private void assignBannerArrayList() {
        switch (dashboardData.getBannerArrayList().size()) {
            case 3:
                setAllBannerData();
                break;
            default: {
                if (dashboardData.getBannerArrayList().size() >= 3) {
                    setAllBannerData();
                }
            }

        }

    }

    private void setAllBannerData() {
        try {
            if (Validation.isValidList(dashboardData.getBannerArrayList().get(0).getBannerdata())) {
                setMainBannerView();
            }
            if (Validation.isValidList(dashboardData.getBannerArrayList().get(1).getBannerdata())) {
                rv_Banner2.setVisibility(View.VISIBLE);
                mBanner2Adapter.addList(dashboardData.getBannerArrayList().get(1).getBannerdata());

            } else {
                rv_Banner2.setVisibility(View.GONE);
            }

            if (Validation.isValidList(dashboardData.getBannerArrayList().get(2).getBannerdata())) {
                rv_banner3.setVisibility(View.VISIBLE);
                mBanner3Adapter.addList(dashboardData.getBannerArrayList().get(2).getBannerdata());

            } else {
                rv_banner3.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setMainBannerView() {
        mainBannerViewPager.setClipToPadding(false);
        mainBannerAdapter = new BannerApter(mContext, R.layout.partial_product_detail_swipe_imageview_list_item, this, Constants.TYPE_MAIN_CATEGORY);
        mainBannerViewPager.setAdapter(mainBannerAdapter);
        mainBannerIndicator.setViewPager(mainBannerViewPager);
        mainBannerAdapter.addArrayList(dashboardData.getBannerArrayList().get(0).getBannerdata());
    }

    private void setupBanner2() {

        mBanner2Adapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image3, this, Constants.TYPE_RV_BANNER_2ADPTER);
        rv_Banner2.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        rv_Banner2.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_insets));
        rv_Banner2.setAdapter(mBanner2Adapter);
        rv_Banner2.setNestedScrollingEnabled(false);
    }

    private void setupShopByBrands() {
        mShopByBrandAdapter = new HomeRecyclerAdapter(mContext, R.layout.item_banner_image3, this, Constants.BRANDS);
        recyclerview_shopByBrand.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        recyclerview_shopByBrand.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_4dp));
        recyclerview_shopByBrand.setAdapter(mShopByBrandAdapter);
        recyclerview_shopByBrand.setNestedScrollingEnabled(false);
    }

    private void setupTopProducts() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        mTopProductAdapter = new ProductListAdapter(mContext, this, rv_top_products, Constants.TOP_PRODUCTS);
        rv_top_products.setLayoutManager(layoutManager);
        rv_top_products.addItemDecoration(CommonUtils.getItemDecoration(rv_top_products.getContext(), layoutManager));
        rv_top_products.setAdapter(mTopProductAdapter);
        rv_top_products.setNestedScrollingEnabled(false);
    }
    private void setupBanner3() {
        mBanner3Adapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_banner_10, this, null);
        rv_banner3.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        rv_banner3.setAdapter(mBanner3Adapter);
    }
    private void updateMultiCatFragmentData(int position) {

        if (Validation.isValidObject(mViewPagerAdapter.getItem(position))) {
            MultipleCatFragment selFrag = (MultipleCatFragment) mViewPagerAdapter.getItem(position);
            selFrag.callMultiCategoryApi(dashboardData.getSub_sub_cat_headers().get(position).get_id());
        }
    }
    @Override
    public void onClickProduct(Object data, int pos, String opType, String adapterType) {
        specificProdType = adapterType;
        if (opType.equals(Constants.PRODUCT_DETAILS) && data instanceof ProductData) {
            ProductData productData = (ProductData) data;
            Intent move = new Intent(mContext, ProductDetailActivity.class);
            move.putExtra(Constants.EXTRA_PRODUCT_ID, productData.get_id());
            startActivity(move);
        }
        if (opType.equals(Constants.PRODUCT_CART_MODIFY1) || opType.equals(Constants.PRODUCT_CART_MODIFY2)) {
            CartModifyParam cartModifyParam = (CartModifyParam) data;
            showLoader(null);
            Sub_SubCategoryPresenter.modifyCart(cartModifyParam);
        }
        if (opType.equals(Constants.PRODUCT_WISH_LIST_CHANGED) && data instanceof ProductData) {
            if (mContext != null) {
                mWishlIstProductData = (ProductData) data;
                showLoader(null);
                Sub_SubCategoryPresenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(mContext), CommonUtils.getSession(mContext));
            }
        }
    }
    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && data instanceof Bannerdata) {
            {
                Bannerdata bannerdata = (Bannerdata) data;
                CommonUtils.navigateBannerData(mContext, bannerdata.getType(), bannerdata.getTarget());
            }
        }
        if (Validation.isValidObject(data) && data instanceof Brands) {
            {
                Brands brands = (Brands) data;
                CommonUtils.navigateBannerData(mContext, Constants.TYPE_BRAND, brands.getBrand_en());
            }
        }
    }
}




