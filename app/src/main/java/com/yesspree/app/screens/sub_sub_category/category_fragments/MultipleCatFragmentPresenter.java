package com.yesspree.app.screens.sub_sub_category.category_fragments;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 09-05-2018.
 */

public class MultipleCatFragmentPresenter implements IMultipleCatFragmentPresenter, IResponseInterface {
    private IMultipleCatFragmentView iCategoryView;
    private IRequestInterface requestInterface;

    public MultipleCatFragmentPresenter(IMultipleCatFragmentView multiCatFragmentView) {
        this.iCategoryView = multiCatFragmentView;
        this.requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {

        iCategoryView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.MULTIPLE_CATEGORY: {
                    iCategoryView.setCategoryApiRes((MultipleCatRespModel) response.body());
                }
                break;
            }
        } else
            iCategoryView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        iCategoryView.hideLoader();
        iCategoryView.showViewState(MultiStateView.VIEW_STATE_ERROR);

    }


    @Override
    public void callMultipleCatData(String mCatId) {

        requestInterface.callApi(AppController.getInstance().service.getMultiplecategoryData(ApiRequestParam.getInstance().getMultiCategoryData(mCatId)), ApiRequestTypes.MULTIPLE_CATEGORY);


    }
}
