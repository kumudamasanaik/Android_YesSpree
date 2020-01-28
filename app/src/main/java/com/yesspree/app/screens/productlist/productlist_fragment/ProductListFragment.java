package com.yesspree.app.screens.productlist.productlist_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.HideShowScrollListener;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.common.ProductListAdapter;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductListFragment extends Fragment implements IProductListFragmentView, IProductListClickListener {

    private OnFragmentInteractionListener mListener;
    Context mContext;
    private String TAG = "ProductListFragment";
    View view;
    Unbinder unbinder;
    ProductListAdapter productAdapter;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.rv_productlist_cat1)
    RecyclerView rv_productlist_cat1;

    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;
    @BindView(R.id.empty_button)
    TextView btnEmptyCart;
    ProductListFragmentPresenter productListFragmentPresenter;
    ProductData mWishlIstProductData = null;
    String source = "";
    ProductListInput input;
    private int PAGE_START = 0, PAGE_SIZE = 20;
    private boolean isLastPage, isLoading;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.et_seacrh_input)
    AppCompatEditText et_seacrh_input;

    public ProductListFragment() {
        // Required empty public constructor
    }


    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        PAGE_START = 0;
    }

    @Override
    public void init() {
        productListFragmentPresenter = new ProductListFragmentPresenter(this);
        tvEpmtyCart.setText(getString(R.string.err_product_list));
        btnEmptyCart.setVisibility(View.GONE);
        if (Validation.isValidObject(mListener))
            mListener.onFragmentInteraction("created");


        et_seacrh_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence data, int start, int before, int count) {

                productAdapter.getFilter().filter(data);
                }

            @Override
            public void afterTextChanged(Editable data) {

            }
        });
    }

    public void setProductListInput(String source, ProductListInput input) {
        this.input = input;
        this.source = source;
        if (Validation.isValidObject(input)) {
            setupCat1();
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            productListFragmentPresenter.callProductList(input, false);
        }
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

    @OnClick(R.id.empty_button)
    void clickEmptyView() {

    }

    @OnClick(R.id.error_button)
    void clickErrorView() {

    }


    private void setupCat1() {
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        productAdapter = new ProductListAdapter(mContext, this, rv_productlist_cat1, Constants.TOP_PRODUCTS);
        rv_productlist_cat1.setLayoutManager(layoutManager);
        rv_productlist_cat1.setAdapter(productAdapter);
        rv_productlist_cat1.addItemDecoration(CommonUtils.getItemDecoration(rv_productlist_cat1.getContext(), layoutManager));
        rv_productlist_cat1.setNestedScrollingEnabled(false);

        rv_productlist_cat1.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {

            }

            @Override
            public void onShow() {

            }

            @Override
            public void onShowAtEnd(boolean show) {
                if (!isLoading && !isLastPage) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        if (NetworkStatus.getInstance().isOnline2(mContext)) {
                            fetchPaginateProducts();
                        }
                    }
                }
            }
        });
    }

    private void fetchPaginateProducts() {
        showLoader(getString(R.string.please_wait));
        input.setStart(PAGE_START);
        isLoading = true;
        productListFragmentPresenter.callProductList(input, true);
    }


    @Override
    public void setPaginatedProductList(ProductMainListingRespModel respModel) {
        isLoading = false;
        if (Validation.isValidObject(respModel) && Validation.isValidList(respModel.getProductDataArrayList())) {
            productAdapter.appendList(respModel.getProductDataArrayList());
            if (respModel.getProductDataArrayList().size() >= PAGE_SIZE)
                isLastPage = false;
            else
                isLastPage = true;
            PAGE_START += PAGE_SIZE;
        } else {
            isLastPage = true;
        }
    }


    @Override
    public void setProductList(ProductMainListingRespModel respModel) {
        if (Validation.isValidObject(respModel)) {
            if (Validation.isValidList(respModel.getProductDataArrayList())) {
                showViewState(MultiStateView.VIEW_STATE_CONTENT);
                productAdapter.addList(respModel.getProductDataArrayList());
                if (respModel.getProductDataArrayList().size() >= PAGE_SIZE)
                    isLastPage = false;
                else
                    isLastPage = true;
                PAGE_START += PAGE_SIZE;
            } else {
                isLastPage = true;
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        } else {
            isLastPage = true;
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickProduct(Object data, int pos, String opType, String specificProdType) {
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
                productListFragmentPresenter.modifyCart(cartModifyParam);
            }
        }

        if (opType.equals(Constants.PRODUCT_WISH_LIST_CHANGED) && data instanceof ProductData) {
            if (mContext != null) {
                mWishlIstProductData = (ProductData) data;
                showLoader(null);
                productListFragmentPresenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(mContext), CommonUtils.getSession(mContext));

            }

        }
    }

    @Override
    public void setCartRes(FetchCartResponse cartRes) {
        if (cartRes == null)
            showMsg(null);
        productAdapter.setModifyCart(cartRes);

    }

    @Override
    public void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse) {
        if (modifyWishlistResponse == null)
            showMsg(null);
        productAdapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);


    }


}
