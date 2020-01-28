package com.yesspree.app.screens.search;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.SearchProdRes;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

public class SearchPresenter implements ISearchPrenter, IResponseInterface {

    ISearchView searchView;
    IRequestInterface iRequestInterface;

    public SearchPresenter(ISearchView searchView) {
        this.searchView = searchView;
        this.iRequestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void callSearch(ProductListInput input) {
        iRequestInterface.callApi(AppController.getInstance().service.getSearchResult(input), ApiRequestTypes.SEARCH_PRODUCTS);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        if (response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.SEARCH_PRODUCTS:
                    searchView.setSearchProductRes((SearchProdRes) response.body());
                    break;
            }
        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        searchView.showMsg(String.valueOf(Constants.RESPONSE_SERVER_ERR));
    }

}
