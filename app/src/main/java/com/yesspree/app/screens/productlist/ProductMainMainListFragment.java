package com.yesspree.app.screens.productlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.customviews.MyViewPager;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.modelapi.Advertisement;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.Items;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SpecificProducts;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.common.FooterProductListAdapter;
import com.yesspree.app.screens.common.ViewPagerAdapter;
import com.yesspree.app.screens.explore.ExploreViewActvity;
import com.yesspree.app.screens.filters.FilterActivity;
import com.yesspree.app.screens.home.adapter.BannerApter;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.screens.productlist.productlist_fragment.ProductListFragment;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductMainMainListFragment extends Fragment implements IProductMainListView, IAdapterClickListener, IProductListClickListener {

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    @BindView(R.id.recycler_trending)
    RecyclerView recyclerTrending;

    @BindView(R.id.recycler_bestprices)
    RecyclerView recyclerBestprices;

    @BindView(R.id.filter_view_pager)
    MyViewPager mSubCatViewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.rv_userlike_products)
    RecyclerView rv_userlike_products;

    @BindView(R.id.tv_trending_header)
    TextView tv_trending_header;

    @BindView(R.id.tv_pricing_header)
    TextView tv_pricing_header;

    @BindView(R.id.tv_userLikeProducts)
    TextView tv_userLikeProducts;

    @BindView(R.id.tv_availiable_products)
    TextView tv_availiable_products;

    @BindView(R.id.layout_user_recmded_productsView)
    View layout_user_recmded_productsView;

    @BindView(R.id.view_trending)
    View viewTrendind;

    @BindView(R.id.view_best_prices)
    View viewBestProces;

    /*Explore view*/
    @BindView(R.id.view_transparent_image_control)
    View layout_explore_view;

    @BindView(R.id.ic_backgrond)
    AppCompatImageView backgroundImage;

    @BindView(R.id.tv_explore)
    TextView tv_explore;

    @BindView(R.id.ic_explore_close)
    AppCompatImageView ic_explore_close;

    @BindView(R.id.tv_explore_query)
    TextView tv_explore_title;

    private View view;
    Unbinder unbinder;
    private String source;
    private Context mContext;
    private String TAG = "ProductMainMainListFragment";
    private BannerApter mainBannerAdapter;
    private HomeRecyclerAdapter mTrendingAdapter;
    private HomeRecyclerAdapter mBestPriceAdapter;
    private ViewPagerAdapter mViewPagerAdapter;
    private FooterProductListAdapter mUserLikeAdapter;
    private ProductMainMainListPresenter productMainListPresenter;
    private ProductMainListingRespModel productListCatData;
    private String mCatId = "";
    private String mCatName = "";
    FragmentActivity mActivity;


    private ArrayList<Items> trendingBannerArrayList;
    private ArrayList<Items> bestPricesBannerArrayList;
    private ArrayList<ProductData> specificProductsArrayList;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<String> categorynNameArrayList;
    private ProductListFragment productListFragment;
    private String prodType = "";
    private ProductListInput productListInput;
    private ArrayList<Advertisement> mExploreList;
    private Boolean first = true;

    public static ProductMainMainListFragment newInstance() {
        ProductMainMainListFragment fragment = new ProductMainMainListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        this.mActivity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCatId = getArguments().getString(Constants.CATEGOPRY_ID);
            mCatName = getArguments().getString(Constants.EXTRAS_CATEGORY_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_product_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        productMainListPresenter = new ProductMainMainListPresenter(this);
        setupTrendingListProducts();
        setupBestPricingListProducts();
        setUserAlsoLikeProducts();
        CallProductListApi();
    }


    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        CallProductListApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        CallProductListApi();
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(mContext, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    @Override
    public void CallProductListApi() {

        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            productMainListPresenter.callMainProductListApi(mCatId, Constants.PRODUCT_LIST_TYPE);
        } else
            showMsg(getString(R.string.error_no_internet));
    }

    @Override
    public void setProductListApiResponce(ProductMainListingRespModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                productListCatData = data;
                setData();
                if (Validation.isValidList(data.getAdvertisement())) {
                    mExploreList = data.getAdvertisement();
                    setupExploreView();
                } else
                    layout_explore_view.setVisibility(View.GONE);

            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void setProductListRes(ProductMainListingRespModel productListRes) {
        if (Validation.isValidStatus(productListRes)) {
            if (Validation.isValidList(productListRes.getProductDataArrayList())) {
                //productListFragment.showViewState(MultiStateView.VIEW_STATE_CONTENT);
                //productListFragment.setProductList(productListRes.getProductDataArrayList());
            } else {
                productListFragment.showViewState(MultiStateView.VIEW_STATE_EMPTY);

            }
        } else {
            productListFragment.showViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void setCartRes(FetchCartResponse cartRes) {

        if (Validation.isValidObject(cartRes)) {
            if (prodType.equals(Constants.USER_MAY_ALSO_LIKE_ADAPTER))
                mUserLikeAdapter.setModifyCart(cartRes);
        }
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @OnClick(R.id.tv_filterg_header)
    public void onViewClicked() {
        Intent move = new Intent(mContext, FilterActivity.class);
        startActivity(move);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickProduct(Object data, int pos, String opType, String adapterType) {
        prodType = adapterType;
        if (opType.equals(Constants.PRODUCT_CART_MODIFY1) && data instanceof CartModifyParam) {
            CartModifyParam cartModifyParam = (CartModifyParam) data;
            showLoader(null);
            productMainListPresenter.modifyCart(cartModifyParam);
        }

        if (Validation.isValidObject(data) && data instanceof ProductData) {
            ProductData item = (ProductData) data;
            if (Validation.isValidString(item.get_id())) {
                Intent move = new Intent(mContext, ProductDetailActivity.class);
                move.putExtra(Constants.EXTRA_PRODUCT_ID, item.get_id());
                startActivity(move);
            }

        }

    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {

        if (Validation.isValidObject(data) && data instanceof Items) {
            Items item = (Items) data;
            if (Validation.isValidString(item.get_id())) {
                Intent move = new Intent(mContext, ProductDetailActivity.class);
                move.putExtra(Constants.EXTRA_PRODUCT_ID, item.get_id());
                startActivity(move);
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
    public void setExploreViewResp(ResponseModel responseModel) {
        if (responseModel == null)
            showMsg(null);
        if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getUrl())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SOURCE, Constants.OFFERS_DETAIL);
            bundle.putString(Constants.EXTRA_EXPLORE_URL, mExploreList.get(0).getUrl());
            CommonUtils.startActivity(mContext, ExploreViewActvity.class, bundle);
        }
    }

    @OnClick(R.id.view_transparent_image_control)
    void showWebViewForExplore() {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            showLoader(null);
            if (Validation.isValidList(mExploreList) && Validation.isValidString(mExploreList.get(0).getId()))
                productMainListPresenter.callExploreClickApi(mExploreList.get(0).getId());
        } else {
            showMsg(getString(R.string.error_no_internet));
        }
    }

    @OnClick(R.id.ic_explore_close)
    void closeExploreView() {
        layout_explore_view.setVisibility(View.GONE);
    }

    private void setData() {

        if (Validation.isValidObject((DashBoardActivity) mActivity) && Validation.isValidString(mCatName))
            ((DashBoardActivity) mActivity).title.setText(mCatName);

        if (Validation.isValidString(productListCatData.getTotal_sku())) {
            tv_availiable_products.setText(mCatName.concat(" - " + productListCatData.getTotal_sku()).concat(" " + mContext.getString(R.string.products)));
        } else {
            tv_availiable_products.setText(mCatName.concat(" - " + " 0 ").concat(getString(R.string.products)));

        }

        if (Validation.isValidList(productListCatData.getCategoryArrayList())) {
            categoryArrayList = productListCatData.getCategoryArrayList();
            categorynNameArrayList = new ArrayList<>();
            for (int i = 0; i < categoryArrayList.size(); i++) {
                if (Validation.isValidString(categoryArrayList.get(i).getName()))
                    categorynNameArrayList.add(categoryArrayList.get(i).getName());
            }
            setupViewPager();
        }
        if (Validation.isValidList(productListCatData.getCatDiscountArrayList())) {

            if (productListCatData.getCatDiscountArrayList().size() >= 2) {
                trendingBannerArrayList = productListCatData.getCatDiscountArrayList().get(0).getItemsArrayList();
                bestPricesBannerArrayList = productListCatData.getCatDiscountArrayList().get(1).getItemsArrayList();

                if (Validation.isValidString(productListCatData.getCatDiscountArrayList().get(0).getTitle()))
                    tv_trending_header.setText(productListCatData.getCatDiscountArrayList().get(0).getTitle());

                if (Validation.isValidString(productListCatData.getCatDiscountArrayList().get(1).getTitle()))
                    tv_pricing_header.setText(productListCatData.getCatDiscountArrayList().get(1).getTitle());


                if (Validation.isValidList(trendingBannerArrayList))
                    mTrendingAdapter.addList(trendingBannerArrayList);
                if (Validation.isValidList(bestPricesBannerArrayList))
                    mBestPriceAdapter.addList(bestPricesBannerArrayList);
            }
        } else {

            viewTrendind.setVisibility(View.GONE);
            viewBestProces.setVisibility(View.GONE);
        }
        if (Validation.isValidList(productListCatData.getSpecificProducts())) {
            ArrayList<SpecificProducts> specificProductsArrayList = productListCatData.getSpecificProducts();
            layout_user_recmded_productsView.setVisibility(View.VISIBLE);
            try {
                if (Validation.isValidString(specificProductsArrayList.get(0).getTitle()))
                    tv_userLikeProducts.setText(specificProductsArrayList.get(0).getTitle());
                mUserLikeAdapter.addList(specificProductsArrayList.get(0).getProductDataArrayList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            layout_user_recmded_productsView.setVisibility(View.GONE);
        }
    }
    private void setupTrendingListProducts() {
        mTrendingAdapter = new HomeRecyclerAdapter(mContext, R.layout.item_main_category, this, Constants.TYPE_TRENDING_ADAPTER);
        recyclerTrending.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerTrending.addItemDecoration(new InsetDecoration(mContext));
        recyclerTrending.setAdapter(mTrendingAdapter);
    }
    private void setupBestPricingListProducts() {
        mBestPriceAdapter = new HomeRecyclerAdapter(mContext, R.layout.item_main_category, this, Constants.TYPE_BEST_PRICES_ADAPTER);
        recyclerBestprices.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerBestprices.addItemDecoration(new InsetDecoration(mContext));
        recyclerBestprices.setAdapter(mBestPriceAdapter);
    }
    private void setupViewPager() {
        setProductListDefInput();
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        if (Validation.isValidList(categorynNameArrayList)) {
            for (String productName : categorynNameArrayList) {
                mViewPagerAdapter.addFragments(ProductListFragment.newInstance(), productName);
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
                setupTabLayoutData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setProductListDefInput() {
        productListInput = new ProductListInput();
        productListInput.set_id(CommonUtils.getCustomerId(mContext));
        productListInput.set_session(CommonUtils.getSession(mContext));
        productListInput.setWh_pincode(CommonUtils.getPincode(mContext));
    }

    private void setupTabLayoutData(int position) {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {

            productListFragment = (ProductListFragment) mViewPagerAdapter.getItem(position);
            if (productListFragment != null) {
                // TODO: 13-06-2018 remove static payload
                if (Validation.isValidList(categoryArrayList)) {
                    productListInput.setId_subcategory(categoryArrayList.get(position).get_id());
                    productListFragment.setProductListInput(Constants.SOURCE_PRODUCT_MAIN_LIST_FRAG, productListInput);
                }
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
        }

    }
    private void setUserAlsoLikeProducts() {

        mUserLikeAdapter = new FooterProductListAdapter(mContext, this, rv_userlike_products, Constants.USER_MAY_ALSO_LIKE_ADAPTER);
        rv_userlike_products.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_userlike_products.addItemDecoration(new InsetDecoration(mContext));
        rv_userlike_products.setAdapter(mUserLikeAdapter);
        rv_userlike_products.setNestedScrollingEnabled(false);
    }

    private void setupExploreView() {
        layout_explore_view.setVisibility(View.VISIBLE);
        if (Validation.isValidString(mExploreList.get(0).getPic()))
            ImageUtils.setImage(backgroundImage, mExploreList.get(0).getPic());
        if (Validation.isValidString(mExploreList.get(0).getTitle()))
            tv_explore_title.setText(mExploreList.get(0).getTitle());
    }
}
