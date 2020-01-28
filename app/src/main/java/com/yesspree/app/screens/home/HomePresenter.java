package com.yesspree.app.screens.home;

import android.content.Context;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.DashboardResModel;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class HomePresenter implements IHomePresenter, IResponseInterface {


    private IHomeView homeView;
    private IRequestInterface requestInterface;
    public String TAG = "HomePresenter";

    public HomePresenter(IHomeView homeView) {
        this.homeView = homeView;
        this.requestInterface = new ApiResponsePresenter(this);
    }


    @Override
    public void callDashboardApi(Context context) {
        requestInterface.callApi(AppController.getInstance().service.getDashboard(ApiRequestParam.getInstance().getDashboardData(context)), ApiRequestTypes.DASHBOARD);
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
    public void callExploreClickApi(String exploreId ) {
        requestInterface.callApi(AppController.getInstance().service.exploreViewClicked(ApiRequestParam.getInstance().getExploreViewData(exploreId)), ApiRequestTypes.EXPLORE_VIEW);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        homeView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.DASHBOARD:
                    homeView.setDashboardApiRes((DashboardResModel) response.body());
                    break;
                case ApiRequestTypes.MODIFY_CART:
                    homeView.setCartRes((FetchCartResponse) response.body());
                    break;
                case ApiRequestTypes.MODIFY_WISH_LIST:
                    homeView.setModifyWishListRes((ModifyWishlistResponse) response.body());
                    break;
                case ApiRequestTypes.EXPLORE_VIEW:
                    homeView.setExploreViewResp((ResponseModel) response.body());
                    break;
            }
        } else
            homeView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        homeView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.DASHBOARD:
                homeView.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
            case ApiRequestTypes.MODIFY_CART:
                homeView.setCartRes(null);
                break;
            case ApiRequestTypes.EXPLORE_VIEW:
                homeView.setExploreViewResp(null);
                break;
        }

    }
}
