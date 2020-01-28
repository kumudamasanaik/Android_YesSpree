package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 22-06-2018.
 */

public class MyAccountRespModel extends ResponseModel {
    @SerializedName(Constants.RESULT)
    private ArrayList<MyAccount> myAccount;

    @SerializedName(Constants.ADDRESS)
    private AddressData addressData;


    public ArrayList<MyAccount> getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(ArrayList<MyAccount> myAccount) {
        this.myAccount = myAccount;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

}
