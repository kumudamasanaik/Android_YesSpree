package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 04-06-2018.
 */

public class ModifyWishlistResponse extends ResponseModel {
    @SerializedName(Constants.RESULT)
    private ArrayList<ProductData> productList;

    public ArrayList<ProductData> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductData> productList) {
        this.productList = productList;
    }

}
