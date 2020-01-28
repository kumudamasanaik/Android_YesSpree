package com.yesspree.app.screens.cart;


import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.utility.CommonUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 07-05-2018.
 */

public class CartActivityPresenter implements ICartActivityPresenter, IResponseInterface {
    ApiResponsePresenter iRequest;
    ICartActivityView view;

    public CartActivityPresenter(ICartActivityView view) {
        this.view = view;
        this.iRequest = new ApiResponsePresenter(this);
    }

    @Override
    public void fetchCart(String id, String session) {
        iRequest.callApi(AppController.getInstance().service.fetchCart(ApiRequestParam.getInstance().fetchCart(id, session)), ApiRequestTypes.FETCH_CART);
    }

    @Override
    public void modifyCart(CartModifyParam modifyParam) {
        iRequest.callApi(AppController.getInstance().service.modifyCart(modifyParam), ApiRequestTypes.MODIFY_CART);
    }

    @Override
    public void removeFromCart(CartModifyParam modifyParam) {
        iRequest.callApi(AppController.getInstance().service.removeFromcart(modifyParam), ApiRequestTypes.REMOVE_FROM_CART);
    }

    @Override
    public void callExploreClickApi(String exploreId) {
        iRequest.callApi(AppController.getInstance().service.exploreViewClicked(ApiRequestParam.getInstance().getExploreViewData(exploreId)), ApiRequestTypes.EXPLORE_VIEW);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        CommonUtils.hideLoading();
        if (response != null && response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.FETCH_CART:
                    view.setFetchRes((FetchCartResponse) response.body());
                    break;
                case ApiRequestTypes.MODIFY_CART:
                    view.setModifyCart((FetchCartResponse) response.body());
                    break;
                case ApiRequestTypes.REMOVE_FROM_CART:
                    view.setRemoveFromCart((FetchCartResponse) response.body());
                    break;
                case ApiRequestTypes.EXPLORE_VIEW:
                    view.setExploreViewResp((ResponseModel) response.body());
                    break;
            }
        } else {
            view.showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        CommonUtils.hideLoading();
        switch (reqType) {
            case ApiRequestTypes.FETCH_CART:
                view.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
            case ApiRequestTypes.MODIFY_CART:
                view.setModifyCart(null);
                break;

            case ApiRequestTypes.EXPLORE_VIEW:
                view.setExploreViewResp(null);
                break;
        }
    }
}
