package com.yesspree.app.productlist;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class ProductListPresenter implements IProductListPresenter, IResponseInterface {

    IProductListView view;
    IRequestInterface iRequestInterface;

    public ProductListPresenter(IProductListView view) {
        this.view = view;
        this.iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void callFilterResult(ProductListInput input) {
        iRequestInterface.callApi(AppController.getInstance().service.getFilterResult(input), ApiRequestTypes.PROD_LIST_RESULT);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.PROD_LIST_RESULT:
                    view.setProductListRes((ProductMainListingRespModel) response.body());
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
