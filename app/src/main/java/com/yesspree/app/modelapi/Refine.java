package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class Refine {

    @SerializedName(Constants.NAME)
    String name;

    @SerializedName(value = Constants.BRAND, alternate = {Constants.OFFER, Constants.PRICE})
    ArrayList<FilterValue> filterValueArrayList;

    boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FilterValue> getFilterValueArrayList() {
        return filterValueArrayList;
    }

    public void setFilterValueArrayList(ArrayList<FilterValue> filterValueArrayList) {
        this.filterValueArrayList = filterValueArrayList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
