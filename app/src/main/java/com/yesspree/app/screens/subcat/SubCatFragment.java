package com.yesspree.app.screens.subcat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.customviews.snap.GravitySnapHelper;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.Advertisement;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SubCategoryResModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.catlastscreen.LastLevCatFragment;
import com.yesspree.app.screens.common.ProductListAdapter;
import com.yesspree.app.screens.explore.ExploreViewActvity;
import com.yesspree.app.screens.home.listner.SubCatAdapterListner;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.screens.productlist.ProductMainMainListFragment;
import com.yesspree.app.screens.subcat.adapter.SubCatCommonRecyclerAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SubCatFragment extends Fragment implements ISubCatView, IAdapterClickListener, IProductListClickListener, SubCatAdapterListner {
    private String TAG = "SubCatFragment";
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    @BindView(R.id.rv_main_cat)
    RecyclerView rvMainCat;

    @BindView(R.id.subCatRecyclerView)
    RecyclerView subCatRecyclerView;

    @BindView(R.id.rv_fixed_squarebanner)
    RecyclerView rv_banner1;

    @BindView(R.id.recycler_AllProducts)
    RecyclerView recycler_AllProducts;

    @BindView(R.id.recycler_topProducts)
    RecyclerView recycler_topProducts;

    @BindView(R.id.recycler_banner2)
    RecyclerView recycler_banner2;

    @BindView(R.id.recycler_banner4)
    RecyclerView recycler_banner4;

    @BindView(R.id.rv_banner5)
    RecyclerView rv_banner5;

    @BindView(R.id.layout_top_products)
    View layout_top_products;

    @BindView(R.id.layout_all_products)
    View layout_all_products;

    @BindView(R.id.layout_shopByBrands)
    View layout_shopByBrands;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    /*Explore view top view*/
    @BindView(R.id.top_explore_view)
    View top_explore_view;

    @BindView(R.id.ic_backgrond)
    AppCompatImageView backgroundImage;

    @BindView(R.id.tv_explore)
    TextView tv_explore;

    @BindView(R.id.ic_explore_close)
    AppCompatImageView ic_explore_close;

    @BindView(R.id.tv_explore_query)
    TextView tv_explore_title;

    /*Explore botom view */

    @BindView(R.id.botom_explore_view)
    View botom_explore_view;

    @BindView(R.id.ic_botom_exploreImage)
    AppCompatImageView mBotomExploreImage;


    @BindView(R.id.ic_botom_explore_close)
    AppCompatImageView ic_botom_explore_close;

    @BindView(R.id.tv_explore_title)
    TextView mBotomExploreTilte;

    //variables
    SubCatPresenter subCatPresenter;
    Context mContext;
    View view;
    Unbinder unbinder;
    SubCatCommonRecyclerAdapter mainCatAdapter;
    SubCatCommonRecyclerAdapter subCatgoryAdapter;
    SubCatCommonRecyclerAdapter rvBanner1_Adapter;
    ProductListAdapter rvTopProductsAdapter;
    SubCatCommonRecyclerAdapter rvBanner2_Adapter;

    ProductListAdapter mAllProductAdapter;
    SubCatCommonRecyclerAdapter rv_Banner4_Adapter;
    SubCatCommonRecyclerAdapter rv_Banner5_Adapter;

    int selPos=0;
    SubCategoryResModel.SubCatData dashboardData;
    ArrayList<Category> categoryArrayList;
    ArrayList<ProductData> mTopProductArrayList;
    ArrayList<ProductData> selectedProductList;

    ArrayList<Brands> brandsArrayList;
    ArrayList<Category> subCatArrayList;

    String mCatId = "";
    String catName = "";
    FragmentActivity mActivity;
    String specificProdType;
    ProductData mWishlIstProductData = null;
    ArrayList<Advertisement> mExploreList;

    MultipleCatRespModel.ChildCategory childCatModel;
    Category curChild;

    public SubCatFragment() {
        // Required empty public constructor
    }

    public static SubCatFragment newInstance() {
        SubCatFragment fragment = new SubCatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Validation.isValidObject(getArguments())) {
            categoryArrayList = getArguments().getParcelableArrayList(Constants.EXTRAS_CATEGORY_LIST);
            selPos = getArguments().getInt(Constants.SEL_POS);
            mCatId = getArguments().getString(Constants.EXTRAS_CATEGORY_ID);
            catName = getArguments().getString(Constants.EXTRAS_CATEGORY_NAME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sub_cat, container, false);
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
    public void init() {
        subCatPresenter = new SubCatPresenter(this);
        setMainCatView();
        setSubCategoryAdapter();
        setBanner1();
        setBanner2();
        setBanner5();
        setupTopProducts();
        setupAllProductList();
        setBanner4();
        callSubCatApi();


    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), true);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();

    }

    @Override
    public void callSubCatApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        subCatPresenter.callSubCatApi(mCatId);
    }


    @Override
    public void setSubCatApiRes(SubCategoryResModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data.getSubCatData())) {
                dashboardData = data.getSubCatData();
                setData();
                if (Validation.isValidList(data.getAdvertisement())) {
                    mExploreList = data.getAdvertisement();
                    setupExploreView();
                } else {
                    top_explore_view.setVisibility(View.GONE);
                    botom_explore_view.setVisibility(View.GONE);
                }
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
                rvTopProductsAdapter.setModifyCart(cartRes);
            if (specificProdType.equals(Constants.ALL_PRODCUTS))
                mAllProductAdapter.setModifyCart(cartRes);
        }
    }

    @Override
    public void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse) {
        if (modifyWishlistResponse == null)
            showMsg(null);

        if (Validation.isValidString(specificProdType)) {
            if (specificProdType.equals(Constants.TOP_PRODUCTS))
                rvTopProductsAdapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);
            if (specificProdType.equals(Constants.ALL_PRODUCTS))
                mAllProductAdapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);
        }

    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callSubCatApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callSubCatApi();
    }

    @Override
    public void setExploreViewResp(ResponseModel responseModel, String mExploretype) {
        if (responseModel == null)
            showMsg(null);
        navigateExploreView(mExploretype);

    }

    @Override
    public void callChildCatagoryData(String childCatID) {
        if (Validation.isValidString(childCatID)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            subCatPresenter.callChildCataData(childCatID);
        }
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

    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        if (Validation.isValidObject(data) && data instanceof Category) {
            if (Validation.isValidObject(categoryArrayList)) {
                for (Category category : categoryArrayList)
                    if (category.isSelectedCategory()) category.setSelectedCategory(false);
                ((Category) data).setSelectedCategory(true);
                mainCatAdapter.notifyDataSetChanged();
                mCatId = ((Category) data).get_id();
                catName = ((Category) data).getName();
                selPos = pos;
                navigateCurrentPositionForMainCatRv();
                callSubCatApi();
                top_explore_view.setVisibility(View.VISIBLE);
            }
        }

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

    @Override
    public void onSubCatAdaptrListner(Object data, int pos, View view) {
        if (Validation.isValidObject(data) && data instanceof Category) {
            curChild = (Category) data;
            callChildCatagoryData(curChild.get_id());
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
            if (data instanceof CartModifyParam) {
                CartModifyParam cartModifyParam = (CartModifyParam) data;
                showLoader(null);
                subCatPresenter.modifyCart(cartModifyParam);
            }
        }

        if (opType.equals(Constants.PRODUCT_WISH_LIST_CHANGED) && data instanceof ProductData) {
            if (mContext != null) {
                mWishlIstProductData = (ProductData) data;
                showLoader(null);
                subCatPresenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(mContext), CommonUtils.getSession(mContext));
            }
        }
    }

    @OnClick(R.id.top_explore_view)
    void showWebViewForExplore() {
        if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getId())) {
            callExporeApi(mExploreList.get(0).getId(), Constants.TOP_EXPLORE);
        }
    }

    @OnClick(R.id.botom_explore_view)
    void showBotomViewForExplore() {
        if (Validation.isValidList(mExploreList) && mExploreList.size() > 1) {
            if (Validation.isValidString(mExploreList.get(1).getId()))
                callExporeApi(mExploreList.get(1).getId(), Constants.BOTOM_EXPLORE);
        }
    }


    @OnClick(R.id.ic_explore_close)
    void closeExploreView() {
        top_explore_view.setVisibility(View.GONE);
    }

    @OnClick(R.id.ic_botom_explore_close)
    void closeBotomExploreView() {
        botom_explore_view.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_all_products, R.id.tv_top_products, R.id.tv_banner_10_view_all})
    void navigateViewAll(View view) {
        switch (view.getId()) {
            case R.id.tv_all_products:
                CommonUtils.navigateViewAll(mContext, Constants.TYPE_ALL_PRODUCTS, Constants.TYPE_ALL_PRODUCTS, mCatId);
                break;
            case R.id.tv_top_products:
                CommonUtils.navigateViewAll(mContext, Constants.TYPE_TOP_PRODUCTS, Constants.TYPE_TOP_PRODUCTS, mCatId);
                break;
            case R.id.tv_banner_10_view_all:
                CommonUtils.navigateViewAll(mContext, Constants.TYPE_BRANDS, Constants.TYPE_BRANDS, mCatId);
                break;
        }


    }


    private void setData() {
        if (Validation.isValidObject((DashBoardActivity) mActivity))
            ((DashBoardActivity) mActivity).title.setText(catName);

        tv_product_name.setText((mActivity.getString(R.string.all)).concat(" " + catName + " ").concat(mActivity.getString(R.string.products)));
        if (Validation.isValidList(dashboardData.getCategoryArrayList())) {
            subCatArrayList = dashboardData.getCategoryArrayList();
            subCatgoryAdapter.addList(subCatArrayList);
        } else {
            subCatRecyclerView.setVisibility(View.GONE);
        }
        if (Validation.isValidList(dashboardData.getTopProductArrayList())) {
            layout_top_products.setVisibility(View.VISIBLE);
            mTopProductArrayList = dashboardData.getTopProductArrayList();
            rvTopProductsAdapter.addList(mTopProductArrayList);
        } else {
            layout_top_products.setVisibility(View.GONE);
        }

        if (Validation.isValidList(dashboardData.getAllProductArrayList())) {
            selectedProductList = dashboardData.getAllProductArrayList();
            mAllProductAdapter.addList(selectedProductList);
            layout_all_products.setVisibility(View.VISIBLE);
        } else {
            layout_all_products.setVisibility(View.GONE);
        }

        if (Validation.isValidList(dashboardData.getBrandsArrayList())) {
            brandsArrayList = dashboardData.getBrandsArrayList();
            if (brandsArrayList.size() > 6) {
                brandsArrayList.subList(6, brandsArrayList.size()).clear();
                rv_Banner4_Adapter.addList(brandsArrayList);
            } else {
                rv_Banner4_Adapter.addList(brandsArrayList);
            }
            layout_shopByBrands.setVisibility(View.VISIBLE);

        } else {
            layout_shopByBrands.setVisibility(View.GONE);
        }

        if (Validation.isValidList(dashboardData.getBannerArrayList())) {
            assignBannerList();
        } else {
            rv_banner1.setVisibility(View.GONE);
            recycler_banner2.setVisibility(View.GONE);
            rv_banner5.setVisibility(View.GONE);
        }


    }

    private void assignBannerList() {
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
                rv_banner1.setVisibility(View.VISIBLE);
                rvBanner1_Adapter.addList(dashboardData.getBannerArrayList().get(0).getBannerdata());
            } else {
                rv_banner1.setVisibility(View.GONE);
            }

            if (Validation.isValidList(dashboardData.getBannerArrayList().get(1).getBannerdata())) {
                rvBanner2_Adapter.addList(dashboardData.getBannerArrayList().get(1).getBannerdata());
                recycler_banner2.setVisibility(View.VISIBLE);

            } else {
                recycler_banner2.setVisibility(View.GONE);
            }

            if (Validation.isValidList(dashboardData.getBannerArrayList().get(2).getBannerdata())) {
                rv_banner5.setVisibility(View.VISIBLE);

                ArrayList<Bannerdata> bannerdata = new ArrayList<>(dashboardData.getBannerArrayList().get(2).getBannerdata());
                if (dashboardData.getBannerArrayList().get(2).getBannerdata().size() > 4) {
                    bannerdata.subList(4, dashboardData.getBannerArrayList().get(2).getBannerdata().size()).clear();
                    rv_Banner5_Adapter.addList(bannerdata);
                } else {
                    rv_Banner5_Adapter.addList(bannerdata);
                }


            } else {
                rv_banner5.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void setMainCatView() {
        mainCatAdapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_main_category, this, null);
        rvMainCat.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvMainCat.addItemDecoration(new InsetDecoration(mContext));
        rvMainCat.setHasFixedSize(true);
        rvMainCat.setAdapter(mainCatAdapter);
        mainCatAdapter.addList(categoryArrayList);
        navigateCurrentPositionForMainCatRv();

    }

    private void navigateCurrentPositionForMainCatRv() {

        if (Validation.isValidObject(rvMainCat))
            new Handler().postDelayed(() -> rvMainCat.smoothScrollToPosition(selPos), 500);
    }


    // done only UI changes
    private void setSubCategoryAdapter() {
        subCatgoryAdapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_banner_image9, this, this);
        subCatRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        subCatRecyclerView.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_insets));
        subCatRecyclerView.setAdapter(subCatgoryAdapter);
        subCatRecyclerView.setHasFixedSize(true);
    }


    private void setBanner1() {
        rvBanner1_Adapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_banner_image2, this, null);
        rv_banner1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_banner1.addItemDecoration(new InsetDecoration(mContext, R.dimen.dimens_4));
        rv_banner1.setAdapter(rvBanner1_Adapter);


    }

    private void setBanner2() {
        rvBanner2_Adapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_banner_image3, this, null);
        recycler_banner2.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        recycler_banner2.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_insets));
        new GravitySnapHelper(Gravity.END).attachToRecyclerView(recycler_banner2);
        recycler_banner2.setHasFixedSize(true);
        recycler_banner2.setAdapter(rvBanner2_Adapter);
    }


    private void setupTopProducts() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvTopProductsAdapter = new ProductListAdapter(mContext, this, recycler_topProducts, Constants.TOP_PRODUCTS);
        recycler_topProducts.setLayoutManager(linearLayoutManager);
        recycler_topProducts.addItemDecoration(CommonUtils.getItemDecoration(recycler_AllProducts.getContext(), linearLayoutManager));
        recycler_topProducts.setAdapter(rvTopProductsAdapter);
        recycler_topProducts.setHasFixedSize(true);
        recycler_topProducts.setNestedScrollingEnabled(false);
    }


    private void setupAllProductList() {
        mAllProductAdapter = new ProductListAdapter(mContext, this, recycler_AllProducts, Constants.ALL_PRODUCTS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recycler_AllProducts.setLayoutManager(linearLayoutManager);
        recycler_AllProducts.addItemDecoration(CommonUtils.getItemDecoration(recycler_AllProducts.getContext(), linearLayoutManager));
        recycler_AllProducts.setAdapter(mAllProductAdapter);
        recycler_AllProducts.setHasFixedSize(true);
        recycler_AllProducts.setNestedScrollingEnabled(false);
    }


    private void setBanner5() {
        rv_Banner5_Adapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_banner_image4, this, null);
        rv_banner5.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        rv_banner5.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_4dp));
        rv_banner5.setAdapter(rv_Banner5_Adapter);
        rv_banner5.setHasFixedSize(true);
        rv_banner5.setNestedScrollingEnabled(false);
    }

    private void setBanner4() {
        rv_Banner4_Adapter = new SubCatCommonRecyclerAdapter(mContext, R.layout.item_banner_image3, this, null);
        recycler_banner4.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        recycler_banner4.addItemDecoration(new InsetDecoration(mContext, R.dimen.card_4dp));
        recycler_banner4.setHasFixedSize(true);
        recycler_banner4.setAdapter(rv_Banner4_Adapter);

    }


    private void setupExploreView() {

        if (Validation.isValidString(mExploreList.get(0).getPic()))
            ImageUtils.setImage(backgroundImage, mExploreList.get(0).getPic());
        if (Validation.isValidString(mExploreList.get(0).getTitle()))
            tv_explore_title.setText(mExploreList.get(0).getTitle());

        if (mExploreList.size() > 1) {
            botom_explore_view.setVisibility(View.VISIBLE);
            if (Validation.isValidString(mExploreList.get(1).getPic()))
                ImageUtils.setImage(mBotomExploreImage, mExploreList.get(1).getPic());
            if (Validation.isValidString(mExploreList.get(1).getTitle()))
                mBotomExploreTilte.setText(mExploreList.get(1).getTitle());
        } else {
            botom_explore_view.setVisibility(View.GONE);
        }
    }


    private void callExporeApi(String exploreID, String type) {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            showLoader(null);
            if (Validation.isValidString(exploreID))
                subCatPresenter.callExploreClickApi(exploreID, type);
        } else {
            showMsg(getString(R.string.error_no_internet));
        }
    }


    private void navigateExploreView(String mExploretype) {
        if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploretype))// Validation.isValidString(mExploreList.get(0).getUrl())) {
        {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SOURCE, Constants.SUB_CATEGORY);
            if (mExploretype.equalsIgnoreCase(Constants.TOP_EXPLORE)) {
                bundle.putString(Constants.EXTRA_EXPLORE_URL, mExploreList.get(0).getUrl());
            } else {
                bundle.putString(Constants.EXTRA_EXPLORE_URL, mExploreList.get(1).getUrl());
            }
            CommonUtils.startActivity(mContext, ExploreViewActvity.class, bundle);
        }
    }

    private void navigateChildeCatData() {
        if (Validation.isValidList(childCatModel.getCategory())) {
            // list havaing category data move into sub sub categoryView
            if (Validation.isValidObject(curChild)) {
                if (getActivity() != null && getActivity() instanceof DashBoardActivity) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CATEGOPRY_ID, curChild.get_id());
                    bundle.putString(Constants.EXTRAS_SUB_CATEGORY_NAME, curChild.getName());
                    LastLevCatFragment lastLevCatFragment = LastLevCatFragment.newInstance();
                    lastLevCatFragment.setArguments(bundle);
                    CommonUtils.addFragment(lastLevCatFragment, mActivity);
                }
            }

        } else {
            // list dont have category data move into ProductListing
            if (Validation.isValidObject(curChild)) {
                if (getActivity() != null && getActivity() instanceof DashBoardActivity) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CATEGOPRY_ID, curChild.get_id());
                    bundle.putString(Constants.EXTRAS_CATEGORY_NAME, curChild.getName());
                    ProductMainMainListFragment subCatFragment = ProductMainMainListFragment.newInstance();
                    subCatFragment.setArguments(bundle);
                    CommonUtils.addFragment(subCatFragment, mActivity);
                }
            }
        }
    }
}
