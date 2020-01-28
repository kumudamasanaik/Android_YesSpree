package com.yesspree.app.screens.productlist.productlist_fragment;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.utility.Validation;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 08-05-2018.
 */

public class ProductListFragmentPresenter implements IResponseInterface, IProductListFragmentPresenter {

    private IProductListFragmentView iProductListFragmentView;
    private IRequestInterface requestInterface;
    public String TAG = "ProductListFragmentPresenter";

    public ProductListFragmentPresenter(IProductListFragmentView iProductListFragmentView) {
        this.iProductListFragmentView = iProductListFragmentView;
        this.requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void callProductList(ProductListInput input, boolean forPagination) {
        if (Validation.isValidString(input.getSku())) {
            requestInterface.callApi(AppController.getInstance().service.getSkuResults(input), (!forPagination) ? ApiRequestTypes.PROD_LIST_RESULT : ApiRequestTypes.PAGINATED_PROD_LIST_RESULT);
        } else if (Validation.isValidString(input.getSearch())) {
            requestInterface.callApi(AppController.getInstance().service.getOffersSearchResults(input), (!forPagination) ? ApiRequestTypes.PROD_LIST_RESULT : ApiRequestTypes.PAGINATED_PROD_LIST_RESULT);

        } else  if (Validation.isValidString(input.getType()))
        {
            requestInterface.callApi(AppController.getInstance().service.getViewAllProducts(input), (!forPagination) ? ApiRequestTypes.PROD_LIST_RESULT : ApiRequestTypes.PAGINATED_PROD_LIST_RESULT);
        }else
            requestInterface.callApi(AppController.getInstance().service.getFilterResult(input), (!forPagination) ? ApiRequestTypes.PROD_LIST_RESULT : ApiRequestTypes.PAGINATED_PROD_LIST_RESULT);
    }


    @Override
    public void modifyCart(CartModifyParam modifyParam) {
        requestInterface.callApi(AppController.getInstance().service.modifyCart(modifyParam), ApiRequestTypes.MODIFY_CART);
    }

    @Override
    public void modifyWishList(String WishListType, String productId, String customerId, String Session) {
        requestInterface.callApi(AppController.getInstance().service.getModifyWishlist(ApiRequestParam.getInstance().modifyWishlist(WishListType, productId, customerId, Session)), ApiRequestTypes.MODIFY_WISH_LIST);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        iProductListFragmentView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.PROD_LIST_RESULT:
                    iProductListFragmentView.setProductList((ProductMainListingRespModel) response.body());
                    break;
                case ApiRequestTypes.PAGINATED_PROD_LIST_RESULT:
                    iProductListFragmentView.setPaginatedProductList((ProductMainListingRespModel) response.body());
                    break;
                case ApiRequestTypes.MODIFY_CART:
                    iProductListFragmentView.setCartRes((FetchCartResponse) response.body());
                    break;
                case ApiRequestTypes.MODIFY_WISH_LIST:
                    iProductListFragmentView.setModifyWishListRes((ModifyWishlistResponse) response.body());
                    break;
            }
        } else
            iProductListFragmentView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        iProductListFragmentView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.PROD_LIST_RESULT:
                iProductListFragmentView.setProductList(null);
                break;
            case ApiRequestTypes.PAGINATED_PROD_LIST_RESULT:
                iProductListFragmentView.setPaginatedProductList(null);
                break;
        }
        iProductListFragmentView.setCartRes(null);
    }

}


