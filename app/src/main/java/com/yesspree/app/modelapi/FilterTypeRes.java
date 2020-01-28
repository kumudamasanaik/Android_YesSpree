package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class FilterTypeRes extends ResponseModel {
    @SerializedName(Constants.REFINE)
    ArrayList<Refine> refineArrayList;
    @SerializedName(Constants.SORT)
    ArrayList<FilterValue> sortArrayList;

    public ArrayList<Refine> getRefineArrayList() {
        return refineArrayList;
    }

    public void setRefineArrayList(ArrayList<Refine> refineArrayList) {
        this.refineArrayList = refineArrayList;
    }

    public ArrayList<FilterValue> getSortArrayList() {
        return sortArrayList;
    }

    public void setSortArrayList(ArrayList<FilterValue> sortArrayList) {
        this.sortArrayList = sortArrayList;
    }
}
