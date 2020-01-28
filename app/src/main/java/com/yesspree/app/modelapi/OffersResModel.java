package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 27-06-2018.
 */

public class OffersResModel extends ResponseModel {
    @SerializedName(Constants.OFFERS)
    private ArrayList<Offers> offersArrayList;

    public ArrayList<Offers> getOffersArrayList() {
        return offersArrayList;
    }

    public void setOffersArrayList(ArrayList<Offers> offersArrayList) {
        this.offersArrayList = offersArrayList;
    }
}
