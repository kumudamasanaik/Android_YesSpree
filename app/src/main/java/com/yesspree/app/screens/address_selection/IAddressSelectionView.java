package com.yesspree.app.screens.address_selection;

import com.yesspree.app.modelapi.FetchAddressResponse;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public interface IAddressSelectionView extends IBaseView{

    void callApiForAddressList();
    void callApiForDeleteAddress();
    void callApiForSelectingAddress();



    void setAddAddressListApiResp(FetchAddressResponse res);
    void deleteAddressResponce(FetchAddressResponse response);
    void selectAddressResponce(FetchAddressResponse response);

    void showViewState(int state);
}
