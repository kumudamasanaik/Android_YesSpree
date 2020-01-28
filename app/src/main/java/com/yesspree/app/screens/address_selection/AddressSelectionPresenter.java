package com.yesspree.app.screens.address_selection;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.FetchAddressResponse;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public class AddressSelectionPresenter implements IResponseInterface, IAddressSelectionPresenter {
    IAddressSelectionView iAddAddressActivityView;
    private IRequestInterface requestInterface;

    public AddressSelectionPresenter(IAddressSelectionView iAddAddressActivityView) {
        this.iAddAddressActivityView = iAddAddressActivityView;
        this.requestInterface = new ApiResponsePresenter(this);

    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {

        iAddAddressActivityView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.GET_ADDRESS: {
                    iAddAddressActivityView.setAddAddressListApiResp((FetchAddressResponse) response.body());
                }
                break;

                case ApiRequestTypes.DELETE_ADDRESS: {
                    iAddAddressActivityView.deleteAddressResponce((FetchAddressResponse) response.body());
                }
                break;

                case ApiRequestTypes.SELECT_ADDRESS: {
                    iAddAddressActivityView.selectAddressResponce((FetchAddressResponse) response.body());
                }
                break;



            }
        } else
            iAddAddressActivityView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        iAddAddressActivityView.hideLoader();
        iAddAddressActivityView.showViewState(MultiStateView.VIEW_STATE_ERROR);

    }


    @Override
    public void getAddressListData(String authorised_key, String operation) {
        requestInterface.callApi(AppController.getInstance().service.getAddressData(authorised_key, ApiRequestParam.getInstance().getAddressData(operation,null,null)), ApiRequestTypes.GET_ADDRESS);

    }

    @Override
    public void deleteAddress(String autorizedKey, String AddressId,String operation) {
        requestInterface.callApi(AppController.getInstance().service.deleteAddress(autorizedKey, ApiRequestParam.getInstance().getAddressData(operation,null,AddressId)), ApiRequestTypes.DELETE_ADDRESS);

    }

    @Override
    public void selectAddressApi(String autorizedKey,  String AddressId, String operation) {
        requestInterface.callApi(AppController.getInstance().service.selectingAddressApi(autorizedKey, ApiRequestParam.getInstance().getAddressData(operation,null,AddressId)), ApiRequestTypes.SELECT_ADDRESS);

    }
}
