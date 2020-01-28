package com.yesspree.app.screens.addaddress;

import com.yesspree.app.modelapi.FetchAddressResponse;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 07-05-2018.
 */

public interface IAddAddressActivityView extends IBaseView {

    void setAddAddressResp(FetchAddressResponse res);
    void setEditddressResp(FetchAddressResponse res);


    void callAddAdressApi();
    void callEditAdressApi();

    void showViewState(int state);


}
