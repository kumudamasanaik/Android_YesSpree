package com.yesspree.app.screens.product_detail;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ProductDetailRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 02-05-2018.
 */

public class ProductDetailPresenter implements IResponseInterface, IProductDetailPresenter {
    private IProductDetailView productDetailView;
    private IRequestInterface requestInterface;
    ProductDetailRespModel customPordDetailRes;

    public ProductDetailPresenter(IProductDetailView productDetailView) {
        this.productDetailView = productDetailView;
        this.requestInterface = new ApiResponsePresenter(this);

    }

    @Override
    public void callProductDetailApi(String mCatId) {

        requestInterface.callApi(AppController.getInstance().service.getProductDetail(ApiRequestParam.getInstance().getProductDetailPairs(mCatId)), ApiRequestTypes.PRODUCT_DETAIL);
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
        productDetailView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.PRODUCT_DETAIL: {
                    productDetailView.setProductDetailApiResponce((ProductDetailRespModel) response.body());
                }
                break;

                case ApiRequestTypes.MODIFY_CART:
                    productDetailView.setCartRes((FetchCartResponse) response.body());
                    break;

                case ApiRequestTypes.MODIFY_WISH_LIST:
                    productDetailView.setModifyWishListRes((ModifyWishlistResponse) response.body());
                    break;
            }
        } else
            productDetailView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        productDetailView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.PRODUCT_DETAIL:
                customPordDetailRes = new ProductDetailRespModel();
                customPordDetailRes.setThrowable(responseError);
                productDetailView.setProductDetailApiResponce(customPordDetailRes);
                break;
        }
    }
}
