package com.yesspree.app.screens.viewall;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ViewAllBrandsRespModel;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 10-07-2018.
 */

public class ViewAllActivityPresenter implements IResponseInterface, IViewAllActivityPresenter {
    IViewAllActivityView view;
    IRequestInterface iRequestInterface;

    public ViewAllActivityPresenter(IViewAllActivityView view) {
        this.view = view;
        this.iRequestInterface = new ApiResponsePresenter(this);
    }
    @Override
    public void callViewAllBrandsApi(ProductListInput input) {
        iRequestInterface.callApi(AppController.getInstance().service.getViewAllBrands(input), ApiRequestTypes.VIEW_ALL_BRANDS);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.VIEW_ALL_BRANDS:
                    view.setViewAllBrandsApiResp((ViewAllBrandsRespModel) response.body());
                    break;
            }
        } else
            view.showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.showViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
