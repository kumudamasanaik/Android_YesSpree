package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 05-06-2018.
 */

public class FetchAddressResponse  extends ResponseModel{

    @SerializedName(Constants.CUSTOMER)
    private CustomerData customerData;

    @SerializedName(Constants.RESULT)
    private ArrayList<AddressData> addressList;


    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public ArrayList<AddressData> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<AddressData> addressList) {
        this.addressList = addressList;
    }
}
