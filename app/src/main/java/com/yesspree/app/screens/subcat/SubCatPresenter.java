package com.yesspree.app.screens.subcat;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SubCategoryResModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 16-05-2018.
 */

public class SubCatPresenter implements ISubCatPresenter, IResponseInterface {

    private ISubCatView subCatView;
    private IRequestInterface requestInterface;
    private String mExploretype;

    public SubCatPresenter(ISubCatView SubCatView) {
        this.subCatView = SubCatView;
        this.requestInterface = new ApiResponsePresenter(this);

    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        subCatView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.SUB_CATEGORY: {
                    subCatView.setSubCatApiRes((SubCategoryResModel) response.body());
                }
                break;

                case ApiRequestTypes.MODIFY_CART:
                    subCatView.setCartRes((FetchCartResponse) response.body());
                    break;

                case ApiRequestTypes.MODIFY_WISH_LIST:
                    subCatView.setModifyWishListRes((ModifyWishlistResponse) response.body());
                    break;

                case ApiRequestTypes.EXPLORE_VIEW:
                    subCatView.setExploreViewResp((ResponseModel) response.body(),mExploretype);
                    break;

                case ApiRequestTypes.MULTIPLE_CATEGORY: {
                    subCatView.setChildCatApiResponce((MultipleCatRespModel) response.body());
                }
                break;


            }
        } else
            subCatView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        subCatView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.DASHBOARD:
                subCatView.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
            case ApiRequestTypes.MODIFY_CART:
                subCatView.setCartRes(null);
                break;

            case ApiRequestTypes.EXPLORE_VIEW:
                subCatView.setExploreViewResp(null,mExploretype);
                break;

            case ApiRequestTypes.MULTIPLE_CATEGORY:
                subCatView.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;

        }
    }

    @Override
    public void callExploreClickApi(String exploreId,String type) {
        mExploretype=type;
        requestInterface.callApi(AppController.getInstance().service.exploreViewClicked(ApiRequestParam.getInstance().getExploreViewData(exploreId)), ApiRequestTypes.EXPLORE_VIEW);
    }


    @Override
    public void callSubCatApi(String mCatId) {
        requestInterface.callApi(AppController.getInstance().service.getSubCategoryData(ApiRequestParam.getInstance().getSubCategoryData(mCatId)), ApiRequestTypes.SUB_CATEGORY);
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
    public void callChildCataData(String mCatId) {
        requestInterface.callApi(AppController.getInstance().service.getMultiplecategoryData(ApiRequestParam.getInstance().getMultiCategoryData(mCatId)), ApiRequestTypes.MULTIPLE_CATEGORY);

    }

}
