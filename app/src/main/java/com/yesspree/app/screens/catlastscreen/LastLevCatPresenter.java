package com.yesspree.app.screens.catlastscreen;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.LastLevelCatRes;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class LastLevCatPresenter implements ILastLevCatPresenter, IResponseInterface {

    ILastLevCatView levCatView;
    private IRequestInterface requestInterface;

    public LastLevCatPresenter(ILastLevCatView levCatView) {
        this.levCatView = levCatView;
        this.requestInterface = new ApiResponsePresenter(this);

    }

    @Override
    public void callLastcatApi(String catId) {
        requestInterface.callApi(AppController.getInstance().service.getLastLevelcat(ApiRequestParam.getInstance().getCategory(catId)), ApiRequestTypes.LAST_LAVEL_CAT);
    }

    @Override
    public void callChildCataData(String mCatId) {
        requestInterface.callApi(AppController.getInstance().service.getMultiplecategoryData(ApiRequestParam.getInstance().getMultiCategoryData(mCatId)), ApiRequestTypes.MULTIPLE_CATEGORY);

    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        levCatView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.LAST_LAVEL_CAT: {
                    levCatView.setLastLevelCatApiResponce((LastLevelCatRes) response.body());
                }
                break;

                case ApiRequestTypes.MULTIPLE_CATEGORY: {
                    levCatView.setChildCatApiResponce((MultipleCatRespModel) response.body());
                }
                break;

            }
        } else
            levCatView.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        levCatView.hideLoader();
        if (reqType.equals(ApiRequestTypes.LAST_LAVEL_CAT))
            levCatView.showViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
