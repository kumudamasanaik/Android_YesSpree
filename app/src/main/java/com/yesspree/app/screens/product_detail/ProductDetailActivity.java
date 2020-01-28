package com.yesspree.app.screens.product_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ProductDetailRespModel;
import com.yesspree.app.modelapi.Sku;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.common.FooterProductListAdapter;
import com.yesspree.app.screens.common.ProductListAdapter;
import com.yesspree.app.screens.home.adapter.BannerApter;
import com.yesspree.app.screens.product_detail.subscription.SubscribeDialogFragment;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends SubBaseActivity implements IProductDetailView, IProductListClickListener, OnFragmentInteractionListener {
    private String source;
    private Context mContext;
    private String TAG = "ProductDetailActivity";
    private BannerApter mainBannerAdapter;
    private ProductListAdapter mSimilearProductAdapter;
    private FooterProductListAdapter mUserLikeProductAdapter;
    private ProductDetailPresenter productDetailPresenter;
    private String mProductId = "51";

    @BindView(R.id.main_banner_pager)
    ViewPager mainBannerViewPager;

    @BindView(R.id.main_banner_indicator)
    CirclePageIndicator mainBannerIndicator;

    @BindView(R.id.recycler_similar_product_list)
    RecyclerView recycler_similar_product_list;

    @BindView(R.id.rv_userlike_products)
    RecyclerView recycler_userlike_products;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    @BindView(R.id.tv_similer_products)
    TextView tv_similer_products;

    @BindView(R.id.tv_userLikeProducts)
    TextView tv_userLikeProducts;


    @BindView(R.id.tv_product_name)
    TextView tv_product_name;


    @BindView(R.id.product_original_price)
    TextView product_original_price;


    @BindView(R.id.product_discount_price)
    TextView product_discount_price;

    @BindView(R.id.product_saved_price)
    TextView product_saved_price;

    @BindView(R.id.tv_sku)
    TextView tvSku;

    @BindView(R.id.tv_product_discription)
    TextView tv_product_discription;

    @BindView(R.id.tv_read_all)
    TextView tv_read_all;

    @BindView(R.id.layout_quantityView)
    View layout_quantityView;
    @BindView(R.id.tv_count_decrease)
    TextView tv_count_decrease;
    @BindView(R.id.tv_quant_increase)
    TextView tv_quant_increase;
    @BindView(R.id.tv_quant_increase_when_0)
    TextView tv_quant_increase_when_0;
    @BindView(R.id.tv_quantitycount)
    TextView tv_quantitycount;

    @BindView(R.id.layout_addbtnView)
    View layout_addbtnView;

    @BindView(R.id.tv_addTo_wishlist)
    TextView tv_addTo_wishlist;

    @BindView(R.id.tv_go_to_cart)
    TextView tvGoto;


    @BindView(R.id.view_similer_products)
    View view_similer_products;

    @BindView(R.id.layout_userlike_products)
    View layout_userlike_products;


    ProductDetailRespModel mProductRespModel;
    boolean isExpanded = false;
    ProductData productData;
    ProductData mWishlIstProductData = null;
    String specificProdType;
    private CartModifyParam inputParameters;
    String prodType = "";
    int subscribProdeCount = 1;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.product_details_header));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product__detail, fragmentLayout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            mProductId = getIntent().getStringExtra(Constants.EXTRA_PRODUCT_ID);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();
    }


    @Override
    public void init() {
        this.mContext = ProductDetailActivity.this;
        productDetailPresenter = new ProductDetailPresenter(this);
        setSimiliarProducts();
        setUserAlsoLikeProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callProductDetailApi();
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(mContext, (msg != null) ? msg : mContext.getString(R.string.error_something_wrong), 1);
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
    public void callProductDetailApi() {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            productDetailPresenter.callProductDetailApi(mProductId);
        } else
            CommonUtils.showToastMsg(mContext, getString(R.string.error_no_internet), 0);
    }

    @Override
    public void setProductDetailApiResponce(ProductDetailRespModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                mProductRespModel = data;
                setData();
            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void callProductCartApi() {

        showViewState(MultiStateView.VIEW_STATE_LOADING);

    }

    @OnClick(R.id.tv_addTo_wishlist)
    void clickAddToWishList() {
        addWishListItem();
    }

    @Override
    public void onClickProduct(Object data, int pos, String opType, String adapterType) {
        prodType = adapterType;

        if (opType.equals(Constants.PRODUCT_DETAILS) && data instanceof ProductData) {
            ProductData productData = (ProductData) data;
            Intent move = new Intent(this, ProductDetailActivity.class);
            move.putExtra(Constants.EXTRA_PRODUCT_ID, productData.get_id());
            startActivity(move);
            finish();
        }


        if (opType.equals(Constants.PRODUCT_CART_MODIFY1) && data instanceof CartModifyParam) {
            CartModifyParam cartModifyParam = (CartModifyParam) data;
            showLoader(null);
            productDetailPresenter.modifyCart(cartModifyParam);
        }

        if (opType.equals(Constants.PRODUCT_WISH_LIST_CHANGED) && data instanceof ProductData) {
            if (mContext != null) {
                mWishlIstProductData = (ProductData) data;
                showLoader(null);
                productDetailPresenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(mContext), CommonUtils.getSession(mContext));
            }
        }
    }

    @Override
    public void setCartRes(FetchCartResponse cartRes) {
        if (cartRes == null)
            showMsg(null);
        if (prodType.equals(Constants.PRODUCT_DETAILS))
            setModifyCart(cartRes);
        if (prodType.equals(Constants.SIMILAR_PRODUCT))
            mSimilearProductAdapter.setModifyCart(cartRes);
        if (prodType.equals(Constants.USER_MAY_ALSO_LIKE_ADAPTER))
            mUserLikeProductAdapter.setModifyCart(cartRes);

    }

    public void setModifyCart(FetchCartResponse object) {
        try {
            if (Validation.isValidObject(inputParameters)) {
                ProductData current = productData;
                if (Validation.isValidStatus(object)) {
                    current.getSkuData().get(current.getSelectedSkuPosition()).setMycart(String.valueOf(current.getSkuData().get(current.getSelectedSkuPosition()).getTempMyCart()));
                    current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(-1);
                    //Cart Summary
                    if (object.getCartCartSummaryDataData() != null) {
                        SharedPreferenceManger.saveCartData(mContext, new Gson().toJson(object.getCartCartSummaryDataData()));
                    }
                } else
                    onModifyCartError(current);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void onModifyCartError(ProductData current) {
        if (current != null) {
            current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(-1);
            setCountView(current.getSkuData().get(current.getSelectedSkuPosition()));
        }
    }

    @Override
    public void callModifyWishListApi() {
        showLoader(null);
        if (NetworkStatus.getInstance().isOnline2(this)) {
            if (CommonUtils.checkLoginStatus(this, mContext.getString(R.string.login_to_add_to_cart))) {
                if (Validation.isValidObject(mWishlIstProductData))
                    productDetailPresenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(this), CommonUtils.getSession(this));
            }
        } else
            showMsg(getString(R.string.error_no_internet));
    }

    @Override
    public void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse) {
        if (modifyWishlistResponse == null)
            showMsg(null);
        if (prodType.equals(Constants.PRODUCT_DETAILS) && Validation.isValidStatus(modifyWishlistResponse)) {
            setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);
        }
        if (prodType.equals(Constants.SIMILAR_PRODUCT)) {
            mSimilearProductAdapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);
        }
    }


    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callProductDetailApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callProductDetailApi();
    }


    @OnClick(R.id.tv_read_all)
    void getReadAllDiscription() {


        if (tv_read_all.getText().toString().equalsIgnoreCase(getString(R.string.product_read_less))) {
            tv_product_discription.setLines(2);
            tv_product_discription.setEllipsize(TextUtils.TruncateAt.END);
            tv_read_all.setText(getString(R.string.product_read_all));


        } else {
            tv_product_discription.setMaxLines(Integer.MAX_VALUE);
            tv_product_discription.setEllipsize(null);
            tv_read_all.setVisibility(View.VISIBLE);
            tv_read_all.setText(getString(R.string.product_read_less));

        }


    }

    @OnClick(R.id.product_weight_view)
    void showSkuDialog() {
        if (Validation.isValidObject(mProductRespModel) && Validation.isValidList(mProductRespModel.getProductData())) {
            productData = mProductRespModel.getProductData().get(0);
            CommonUtils.showSkuDialog(mContext, mProductRespModel.getProductData().get(0), data -> setSelectedSkuPrice(productData.getSelSku()));

        }
    }


    void addWishListItem() {
        prodType = Constants.PRODUCT_DETAILS;
        if (Validation.isValidObject(mProductRespModel) && Validation.isValidList(mProductRespModel.getProductData())) {
            mWishlIstProductData = mProductRespModel.getProductData().get(0);
            setupWishListData(mWishlIstProductData);
        }
    }

    void setMainBannerView() {
        mainBannerViewPager.setClipToPadding(false);
        mainBannerAdapter = new BannerApter(mContext, new ArrayList<>(), R.layout.partial_product_detail_swipe_imageview_list_item);
        mainBannerViewPager.setAdapter(mainBannerAdapter);
        mainBannerIndicator.setViewPager(mainBannerViewPager);
    }


    private void setSimiliarProducts() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mSimilearProductAdapter = new ProductListAdapter(mContext, this, recycler_similar_product_list, Constants.SIMILAR_PRODUCT);
        recycler_similar_product_list.setLayoutManager(linearLayoutManager);
        recycler_similar_product_list.addItemDecoration(CommonUtils.getItemDecoration(recycler_similar_product_list.getContext(), linearLayoutManager));
        recycler_similar_product_list.setAdapter(mSimilearProductAdapter);
        recycler_similar_product_list.setNestedScrollingEnabled(false);
    }


    private void setUserAlsoLikeProducts() {

        mUserLikeProductAdapter = new FooterProductListAdapter(mContext, this, recycler_userlike_products, Constants.USER_MAY_ALSO_LIKE_ADAPTER);
        recycler_userlike_products.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recycler_userlike_products.addItemDecoration(new InsetDecoration(mContext));
        recycler_userlike_products.setAdapter(mUserLikeProductAdapter);
        recycler_userlike_products.setNestedScrollingEnabled(false);
    }


    private void setData() {
        if (Validation.isValidList(mProductRespModel.getProductData())) {
            setMainBannerView();
            productData = mProductRespModel.getProductData().get(0);
            mainBannerAdapter.addArrayList(mProductRespModel.getProductData());
            setupProductDetailUI();
        }

        if (Validation.isValidObject(mProductRespModel.getSimilerData()) && mProductRespModel.getSimilerData().getProductDataArrayList().size()>0) {
            if (Validation.isValidString(mProductRespModel.getSimilerData().getTitle()))
                tv_similer_products.setText(mProductRespModel.getSimilerData().getTitle());
            view_similer_products.setVisibility(View.VISIBLE);
            mSimilearProductAdapter.addList(mProductRespModel.getSimilerData().getProductDataArrayList());
        } else view_similer_products.setVisibility(View.GONE);


        if (Validation.isValidList(mProductRespModel.getFooterData()) && mProductRespModel.getFooterData().get(0).getProductDataArrayList().size() > 0) {
            tv_userLikeProducts.setText(mProductRespModel.getFooterData().get(0).getTitle());
            layout_userlike_products.setVisibility(View.VISIBLE);
            mUserLikeProductAdapter.addList(mProductRespModel.getFooterData().get(0).getProductDataArrayList());

        } else layout_userlike_products.setVisibility(View.GONE);

        if (Validation.isValidList(mProductRespModel.getProductData()) && Validation.isValidObject(mProductRespModel.getProductData().get(0).getSelSku()) && Validation.isValidString(mProductRespModel.getProductData().get(0).getSelSku().getSize()))
            tvSku.setText(mProductRespModel.getProductData().get(0).getSelSku().getSize());
        else
            tvSku.setText("0");
    }

    @OnClick(R.id.tv_go_to_cart)
    void onClickGoto() {
        if (CommonUtils.isLoggedIn(mContext)) {
            if (tvGoto.getText().toString().equals(getString(R.string.subscribe))) {
                setSubscriptionDialog();
            } else if (tvGoto.getText().toString().equals(getString(R.string.go_to_cart))) {
                startCartScreen(this, Constants.SOURCE_PRODUCT_DETAILS);
            }
        } else
            showMsg(getString(R.string.err_login_specific, getString(R.string.view)));
    }

    private void setSubscriptionDialog() {
        if (Validation.isValidObject(productData) && Validation.isValidObject(productData.getSelSku())) {
            productData.getSelSku().setMycart(String.valueOf(tv_quantitycount.getText().toString()));
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(Constants.SUBSCRIBE_DIALOG_FRAGMENT);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            SubscribeDialogFragment dialogFragment = SubscribeDialogFragment.newInstance(productData);
            dialogFragment.show(ft, Constants.SUBSCRIBE_DIALOG_FRAGMENT);
        } else
            showMsg(getString(R.string.please_try_later));
    }

    @Override
    public void onFragmentInteraction(Object data) {

    }

    private void setupProductDetailUI() {
        if (Validation.isValidList(mProductRespModel.getProductData())) {

            if (Validation.isValidObject(productData.getIs_subscribe())) {
                layout_addbtnView.setVisibility(View.GONE);
                layout_quantityView.setVisibility(View.VISIBLE);
                tv_quantitycount.setText(String.valueOf(subscribProdeCount));
                tvGoto.setText(productData.getIs_subscribe().equals("1") ? getString(R.string.subscribe) : getString(R.string.go_to_cart));
            }
            if (Validation.isValidString(productData.getDescription())) {
                setupDiscriptionView(productData);
                setWishlistBtnState(productData.getWishlist(), productData.getTempWishlistStatus());
            }

            if (Validation.isValidString(productData.getName()))
                tv_product_name.setText(productData.getName());


            if (Validation.isValidDigit(productData.getMrp())) {
                product_original_price.setText(CommonUtils.getStrWithRsSym(mContext, productData.getMrp()));
                CommonUtils.strikeThrText(product_original_price);
                product_original_price.setPaintFlags(product_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                product_original_price.setText(CommonUtils.getStrWithRsSym(mContext, 0));
                CommonUtils.strikeThrText(product_original_price);
            }

            if (Validation.isValidDigit(productData.getSelling_price()))
                product_discount_price.setText(CommonUtils.getStrWithRsSym(mContext, productData.getSelling_price()));
            else
                product_discount_price.setText(CommonUtils.getStrWithRsSym(mContext, 0));


            if (Validation.isValidDigit(productData.getSelling_price()) && (Validation.isValidDigit(productData.getMrp()))) {
                float saved = (productData.getMrp() - productData.getSelling_price()) / productData.getMrp();
                if (Validation.isValidDigit(saved)) {
                    product_saved_price.setText(getString(R.string.you_save).concat(String.valueOf((int) saved) + " %"));
                } else
                    product_saved_price.setText(getString(R.string.you_save).concat("0 %"));
            } else
                product_saved_price.setText(getString(R.string.you_save).concat("0 %"));

            if (!Validation.isValidObject(productData.getSelSku()) && Validation.isValidList(productData.getSkuData())) {
                productData.setSelSku(productData.getSkuData().get(0));
                setSelectedSkuPrice(productData.getSkuData().get(0));
            } else
                setSelectedSkuPrice(productData.getSelSku());

        } else
            showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }


    private void setupDiscriptionView(ProductData data) {
        tv_product_discription.setText(data.getDescription());

        if (tv_product_discription.getMaxLines() > 2) {
            tv_product_discription.setLines(2);
            tv_product_discription.setEllipsize(TextUtils.TruncateAt.END);
            tv_read_all.setText(getString(R.string.product_read_all));
            tv_read_all.setVisibility(View.VISIBLE);


        } else {
            tv_product_discription.setMaxLines(Integer.MAX_VALUE);
            tv_product_discription.setEllipsize(null);
            tv_read_all.setVisibility(View.GONE);
            tv_read_all.setText(getString(R.string.product_read_less));

        }

    }


    public void setSelectedSkuPrice(Sku sku) {
        if (Validation.isValidObject(sku)) {
            int saved;
            tvSku.setText(Validation.isValidString(sku.getSize()) ? sku.getSize() : "0");

            if (Validation.isValidDigit(productData.getSelling_price()) && (Validation.isValidDigit(productData.getMrp()))) {
                saved = (productData.getMrp() - productData.getSelling_price()) / productData.getMrp();
                if (Validation.isValidDigit(saved)) {
                    product_saved_price.setText(getString(R.string.you_save).concat(String.valueOf((int) saved) + " %"));
                } else
                    product_saved_price.setText(getString(R.string.you_save) + "0 %");
            } else
                product_saved_price.setText(getString(R.string.you_save) + "0 %");

            if (Validation.isValidString(sku.getMrp())) {
                product_original_price.setText(CommonUtils.getStrWithRsSym(this, Float.parseFloat(sku.getMrp())));
                CommonUtils.strikeThrText(product_original_price);
            } else {
                product_original_price.setText(CommonUtils.getStrWithRsSym(this, 0));
                CommonUtils.strikeThrText(product_original_price);
            }
            if (Validation.isValidDigit(productData.getSelling_price()))
                product_discount_price.setText(CommonUtils.getStrWithRsSym(this, sku.getSelling_price()));
            else
                product_discount_price.setText(CommonUtils.getStrWithRsSym(this, 0));
            setCountView(productData.getSelSku());
        }
    }


    private void setCountView(Sku skuData) {
        if (tvGoto.getText().toString().equals(getString(R.string.go_to_cart))) {
            tv_quantitycount.setText(String.valueOf(skuData.getTempMyCart() != -1 ? skuData.getTempMyCart() : skuData.getMycart()));
            if ((skuData.getTempMyCart() != -1 ? skuData.getTempMyCart() : skuData.getMycart()) <= 0) {
                layout_addbtnView.setVisibility(View.VISIBLE);
                layout_quantityView.setVisibility(View.GONE);
            } else {
                layout_addbtnView.setVisibility(View.GONE);
                layout_quantityView.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.tv_quant_increase, R.id.tv_quant_increase_when_0, R.id.tv_count_decrease})
    public void clickForModifyCart(View view) {
        prodType = Constants.PRODUCT_DETAILS;
        if (!CommonUtils.checkLoginStatus(mContext, getString(R.string.login_to_add_to_cart))) {
            return;
        }
        if (tvGoto.getText().toString().equals(getString(R.string.subscribe))) {
            if (view.getId() == R.id.tv_quant_increase || view.getId() == R.id.tv_quant_increase_when_0) {
                tv_quantitycount.setText(String.valueOf(++subscribProdeCount));
            } else if (subscribProdeCount > 1)
                tv_quantitycount.setText(String.valueOf(--subscribProdeCount));
        } else if (Validation.isValidList(productData.getSkuData())) {
            if (productData.getSkuData().get(productData.getSelectedSkuPosition()).getTempMyCart() != -1)
                return;
            if (NetworkStatus.getInstance().isOnline2(mContext)) {
                if (view.getId() == R.id.tv_quant_increase || view.getId() == R.id.tv_quant_increase_when_0) {
                    if (Float.valueOf(productData.getSkuData().get(productData.getSelectedSkuPosition()).getStock()) <= productData.getSkuData().get(productData.getSelectedSkuPosition()).getMycart()) {
                        Toast.makeText(mContext, mContext.getString(R.string.cannot_add_items_out_of_stock), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (productData.getSkuData().get(productData.getSelectedSkuPosition()).getMycart() < productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity()) {
                            if (productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity() > 1)
                                Toast.makeText(mContext, mContext.getString(R.string.should_be_minimum_quantity, productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity()), Toast.LENGTH_SHORT).show();
                            productData.getSkuData().get(productData.getSelectedSkuPosition()).setTempMyCart(productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity());
                        } else
                            productData.getSkuData().get(productData.getSelectedSkuPosition()).setTempMyCart(productData.getSkuData().get(productData.getSelectedSkuPosition()).getMycart() + 1);
                    }
                } else {
                    if (productData.getSkuData().get(productData.getSelectedSkuPosition()).getMycart() <= productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity()) {
                        if (productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity() > 1)
                            Toast.makeText(mContext, mContext.getString(R.string.should_be_minimum_quantity, productData.getSkuData().get(productData.getSelectedSkuPosition()).getMin_quantity()), Toast.LENGTH_SHORT).show();
                        productData.getSkuData().get(productData.getSelectedSkuPosition()).setTempMyCart(0);
                    } else
                        productData.getSkuData().get(productData.getSelectedSkuPosition()).setTempMyCart(productData.getSkuData().get(productData.getSelectedSkuPosition()).getMycart() - 1);
                }
                inputParameters = new CartModifyParam();
                inputParameters.setId_product(productData.getProductId());
                inputParameters.setId_sku(productData.getSelSku().get_id());
                inputParameters.setQuantity(String.valueOf(productData.getSkuData().get(productData.getSelectedSkuPosition()).getTempMyCart()));
                inputParameters.set_session(CommonUtils.getSession(mContext));
                inputParameters.setOp(Constants.CART_MODIFY);
                inputParameters.set_id(CommonUtils.getCustomerId(mContext));
                inputParameters.setWh_pincode("123456");
                setCountView(productData.getSelSku());
                showLoader(null);
                productDetailPresenter.modifyCart(inputParameters);
            }
        } else
            CommonUtils.showToastMsg(mContext, getString(R.string.err_no_sku_found), 0);
    }

    @OnClick(R.id.tv_view_all_similarProducts)
    void navigateViewAll(View view) {
        switch (view.getId()) {
            case R.id.tv_view_all_similarProducts:
                CommonUtils.navigateViewAll(mContext, Constants.TYPE_SIMILAR_PRODUCTS, Constants.TYPE_SIMILAR_PRODUCTS, null);

                break;
        }
    }

        private void setupWishListData(ProductData productData) {
        if (!CommonUtils.checkLoginStatus(this, getString(R.string.login_to_wishlist))) {
            return;
        }

        if (productData.getTempWishlistStatus() != -1)
            return;

        if (NetworkStatus.getInstance().isOnline2(this)) {
            productData.setTempWishlistStatus(productData.getWishlist() == 1 ? 0 : 1);
            setWishlistBtnState(productData.getWishlist(), productData.getTempWishlistStatus());
            callModifyWishListApi();
        } else {
            Toast.makeText(this, getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void setWishlistBtnState(int wishlist, int tempWishlistStatus) {
        if (tempWishlistStatus != -1) {
            tv_addTo_wishlist.setText(tempWishlistStatus == 1 ? getString(R.string.product_remove_wish_list) : getString(R.string.product_add_wish_list));
        } else {
            tv_addTo_wishlist.setText(wishlist == 1 ? getString(R.string.product_remove_wish_list) : getString(R.string.product_add_wish_list));
        }
    }


    public void setApiRespFromModifyWishList(ModifyWishlistResponse modifyWishlistResponse, ProductData wishlistproductData) {
        try {
            if (Validation.isValidObject(modifyWishlistResponse)) {
                wishlistproductData.setWishlist(wishlistproductData.getTempWishlistStatus());
                wishlistproductData.setTempWishlistStatus(-1);
            } else {
                onModifyWishlistError(wishlistproductData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onModifyWishlistError(ProductData current) {
        if (current != null) {
            current.setTempWishlistStatus(-1);
            setWishlistBtnState(current.getWishlist(), current.getTempWishlistStatus());
        }
    }
}
