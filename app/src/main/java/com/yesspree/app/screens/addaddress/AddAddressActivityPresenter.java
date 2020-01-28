package com.yesspree.app.screens.addaddress;

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
 * Created by FuGenX-14 on 07-05-2018.
 */

public class AddAddressActivityPresenter implements IResponseInterface, IAddAddressActivityPresenter {

    IAddAddressActivityView iAddAddressActivityView;
    private IRequestInterface requestInterface;

    public AddAddressActivityPresenter(IAddAddressActivityView iAddAddressActivityView) {
        this.iAddAddressActivityView = iAddAddressActivityView;
        this.requestInterface = new ApiResponsePresenter(this);

    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {

        iAddAddressActivityView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.ADD_ADDRESS: {
                    iAddAddressActivityView.setAddAddressResp((FetchAddressResponse) response.body());
                }
                break;

                case ApiRequestTypes.EDIT_ADDRESS: {
                    iAddAddressActivityView.setEditddressResp((FetchAddressResponse) response.body());
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
    public void addAddress(AdressModelToValidation modelToValidation) {
        requestInterface.callApi(AppController.getInstance().service.addAddressData(modelToValidation.authorised_key, ApiRequestParam.getInstance().getAddressData(modelToValidation.op,modelToValidation,"")), ApiRequestTypes.ADD_ADDRESS);

    }

    @Override
    public void editAddress(AdressModelToValidation modelToValidation) {
        requestInterface.callApi(AppController.getInstance().service.editAddressData(modelToValidation.authorised_key, ApiRequestParam.getInstance().getAddressData(modelToValidation.op,modelToValidation,modelToValidation.AddressID)), ApiRequestTypes.EDIT_ADDRESS);

    }
}
