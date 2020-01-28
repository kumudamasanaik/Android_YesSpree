package com.yesspree.app.screens.wishlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.common.ProductListAdapter;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WishListFragment extends Fragment implements IWishListView, IProductListClickListener {


    @BindView(R.id.empty_textView)
    TextView tvEpmtyText;
    @BindView(R.id.empty_button)
    TextView btnEmptyView;
    @BindView(R.id.rv_wishlist)
    RecyclerView rvWishlist;
    @BindView(R.id.multistateview)
    MultiStateView multistateview;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private Context context;
    IWishListPresenter presenter;
    ProductListAdapter rvWishListAdapter;
    ModifyWishlistResponse modifyWishlistResponse;
    private ProductData mWishlIstProductData;


    public WishListFragment() {
        // Required empty public constructor
    }

    public static WishListFragment newInstance() {
        WishListFragment fragment = new WishListFragment();
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
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        presenter = new WishListPresenter(this);
        tvEpmtyText.setText(getString(R.string.err_empty_wish_list));
        btnEmptyView.setVisibility(View.GONE);
        setWishList();
        callWishListApi();
    }

    @Override
    public void showMsg(String msg) {

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
    public void setWishListApiResponce(ModifyWishlistResponse data) {
        if (multistateview != null) {
            if (Validation.isValidStatus(data)) {
                modifyWishlistResponse = data;
                showViewState(MultiStateView.VIEW_STATE_CONTENT);
                if (Validation.isValidList(data.getProductList())) {
                    setWishListProducts();
                } else
                    showViewState(MultiStateView.VIEW_STATE_EMPTY);
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        }

    }

    private void setWishListProducts() {

        if (Validation.isValidList(modifyWishlistResponse.getProductList())) {
            rvWishListAdapter.addList(modifyWishlistResponse.getProductList());
        }

    }

    @Override
    public void callWishListApi() {

        if (CommonUtils.checkLoginStatus(context, getString(R.string.error_user_not_login))) {

            if (NetworkStatus.getInstance().isOnline2(context)) {
                showViewState(MultiStateView.VIEW_STATE_LOADING);
                presenter.callWishListApi(CommonUtils.getCustomerId(context));

            } else {
                showMsg(getString(R.string.error_no_internet));
                showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }


    }

    @Override
    public void showViewState(int state) {
        if (multistateview != null)
            multistateview.setViewState(state);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callWishListApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callWishListApi();
    }

    private void setWishList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        rvWishListAdapter = new ProductListAdapter(context, this, rvWishlist, Constants.WISHLIST);
        rvWishlist.setLayoutManager(layoutManager);
        rvWishlist.addItemDecoration(CommonUtils.getItemDecoration(rvWishlist.getContext(), layoutManager));
        rvWishlist.setAdapter(rvWishListAdapter);
        rvWishlist.setNestedScrollingEnabled(false);
    }


    @Override
    public void onClickProduct(Object data, int pos, String opType, String specificProdType) {
        if (opType.equals(Constants.PRODUCT_CART_MODIFY1)) {
            CartModifyParam cartModifyParam = (CartModifyParam) data;
            showLoader(null);
            presenter.modifyCart(cartModifyParam);
        }

        if (opType.equals(Constants.PRODUCT_WISH_LIST_CHANGED) && data instanceof ProductData) {
            mWishlIstProductData = (ProductData) data;
            showLoader(null);
            presenter.modifyWishList(mWishlIstProductData.getWishlist() == 1 ? Constants.WISHLIST_DELETE : Constants.WISHLIST_CREATE, mWishlIstProductData.getProductId(), CommonUtils.getCustomerId(context), CommonUtils.getSession(context));
        }

        if (opType.equals(Constants.PRODUCT_DETAILS) && data instanceof ProductData) {
            ProductData productData = (ProductData) data;
            Intent move = new Intent(context, ProductDetailActivity.class);
            move.putExtra(Constants.EXTRA_PRODUCT_ID, productData.get_id());
            startActivity(move);
        }
    }

    @Override
    public void setCartRes(FetchCartResponse cartRes) {
        if (cartRes == null)
            showMsg(null);
        rvWishListAdapter.setModifyCart(cartRes);
    }

    @Override
    public void setModifyWishListRes(ModifyWishlistResponse modifyWishlistResponse) {

        if (Validation.isValidStatus(modifyWishlistResponse)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidList(modifyWishlistResponse.getProductList())) {
                rvWishListAdapter.setApiRespFromModifyWishList(modifyWishlistResponse, mWishlIstProductData);

            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);


    }
}
