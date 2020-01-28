package com.yesspree.app.screens.address_selection;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public interface IAddressSelectionPresenter {

    void getAddressListData(String autorizedKey,String operation);
    void deleteAddress(String autorizedKey,String AddressId,String operation);
    void selectAddressApi(String autorizedKey,String AddressId, String operation);

}
