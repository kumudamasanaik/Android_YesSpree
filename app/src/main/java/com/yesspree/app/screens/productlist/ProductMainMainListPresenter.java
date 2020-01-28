package com.yesspree.app.screens.productlist;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 08-05-2018.
 */

public class ProductMainMainListPresenter implements IResponseInterface, IProductMainListPresenter {
    private IProductMainListView productListView;
    private IRequestInterface requestInterface;

    public ProductMainMainListPresenter(IProductMainListView productListView) {
        this.productListView = productListView;
        this.requestInterface = new ApiResponsePresenter(this);

    }

    @Override
    public void callMainProductListApi(String mCatId, int type) {
        requestInterface.callApi(AppController.getInstance().service.getProductMainListCat(ApiRequestParam.getInstance().getMainProductListData(mCatId, type)), ApiRequestTypes.PRODUCT_MAIN_LISTING);
    }

    @Override
    public void callProductList(String mCatId, int type) {
        requestInterface.callApi(AppController.getInstance().service.getProductList(ApiRequestParam.getInstance().getMainProductListData(mCatId, type)), ApiRequestTypes.PRODUCT_LISTING);
    }

    @Override
    public void modifyCart(CartModifyParam modifyParam) {
        requestInterface.callApi(AppController.getInstance().service.modifyCart(modifyParam), ApiRequestTypes.MODIFY_CART);
    }
    @Override
    public void callExploreClickApi(String exploreId) {
        requestInterface.callApi(AppController.getInstance().service.exploreViewClicked(ApiRequestParam.getInstance().getExploreViewData(exploreId)), ApiRequestTypes.EXPLORE_VIEW);
    }
    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        productListView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.PRODUCT_MAIN_LISTING:
                    productListView.setProductListApiResponce((ProductMainListingRespModel) response.body());
                    break;
                case ApiRequestTypes.MODIFY_CART:
                    productListView.setCartRes((FetchCartResponse) response.body());
                    break;
                case ApiRequestTypes.PRODUCT_LISTING:
                    productListView.setProductListRes((ProductMainListingRespModel) response.body());
                    break;
                case ApiRequestTypes.EXPLORE_VIEW:
                    productListView.setExploreViewResp((ResponseModel) response.body());
                    break;
            }
        } else
            productListView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        productListView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.PRODUCT_MAIN_LISTING:
                productListView.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
            case ApiRequestTypes.EXPLORE_VIEW:
                productListView.setExploreViewResp(null);
                break;
        }
    }
}
