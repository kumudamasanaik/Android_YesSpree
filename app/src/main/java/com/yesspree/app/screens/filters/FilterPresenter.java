package com.yesspree.app.screens.filters;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.FilterTypeRes;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 30-04-2018.
 */

public class FilterPresenter implements IFilterPresenter, IResponseInterface {
    IFilterView view;
    IRequestInterface requestInterface;

    public FilterPresenter(IFilterView view) {
        this.view = view;
        requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void callFilterType(String catId) {
        requestInterface.callApi(AppController.getInstance().service.getFilterType(ApiRequestParam.getInstance().getFilterType(catId)), ApiRequestTypes.FILTER_TYPE);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        switch (reqType) {
            case ApiRequestTypes.FILTER_TYPE:
                view.setFilterTypeRes((FilterTypeRes) response.body());
                break;
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {

    }
}
