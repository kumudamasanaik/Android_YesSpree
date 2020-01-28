package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 11-07-2018.
 */

public class ViewAllBrandsRespModel extends ResponseModel {

    @SerializedName(Constants.BRANDS)
    private ArrayList<Brands> brandsArrayList;

    public ArrayList<Brands> getBrandsArrayList() {
        return brandsArrayList;
    }

    public void setBrandsArrayList(ArrayList<Brands> brandsArrayList) {
        this.brandsArrayList = brandsArrayList;
    }
}
