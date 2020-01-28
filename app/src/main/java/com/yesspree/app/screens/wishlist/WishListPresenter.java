package com.yesspree.app.screens.wishlist;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

import static com.yesspree.app.constatnts.Constants.WISHLIST_DATA;

/**
 * Created by FuGenX-14 on 07-06-2018.
 */

public class WishListPresenter implements IWishListPresenter, IResponseInterface {

    private IWishListView iWishListView;
    private IRequestInterface requestInterface;
    ModifyWishlistResponse customRes;

    public WishListPresenter(IWishListView wishListView) {
        this.iWishListView = wishListView;
        this.requestInterface = new ApiResponsePresenter(this);

    }

    @Override
    public void callWishListApi(String custID) {
        requestInterface.callApi(AppController.getInstance().service.getModifyWishlist(ApiRequestParam.getInstance().getWishlist(WISHLIST_DATA, custID)), ApiRequestTypes.WISH_LIST);
    }

    @Override
    public void modifyCart(CartModifyParam modifyParam) {
        requestInterface.callApi(AppController.getInstance().service.modifyCart(modifyParam), ApiRequestTypes.MODIFY_CART);
    }

    @Override
    public void modifyWishList(String mWishLIstType, String ProductId, String customerId, String Session) {
        requestInterface.callApi(AppController.getInstance().service.getModifyWishlist(ApiRequestParam.getInstance().modifyWishlist(mWishLIstType, ProductId, customerId, Session)), ApiRequestTypes.MODIFY_WISH_LIST);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        iWishListView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.WISH_LIST: {
                    iWishListView.setWishListApiResponce((ModifyWishlistResponse) response.body());
                }
                break;
                case ApiRequestTypes.MODIFY_CART:
                    iWishListView.setCartRes((FetchCartResponse) response.body());
                    break;

                case ApiRequestTypes.MODIFY_WISH_LIST:
                    iWishListView.setModifyWishListRes((ModifyWishlistResponse) response.body());
                    break;
            }
        } else
            iWishListView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        customRes = new ModifyWishlistResponse();
        customRes.setThrowable(responseError);
        iWishListView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.MODIFY_WISH_LIST: {
                iWishListView.setWishListApiResponce(customRes);
                iWishListView.showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
            break;
        }
    }

}
