package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class UserData extends ResponseModel {
    @SerializedName(Constants.RESULT)
    private ArrayList<CustomerData> customerList;

    public ArrayList<CustomerData> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<CustomerData> customerList) {
        this.customerList = customerList;
    }
}