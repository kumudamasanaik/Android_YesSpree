package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CatDiscount {
    @SerializedName("title")
    public String title;
    @SerializedName("items")
    public ArrayList<Items> itemsArrayList;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Items> getItemsArrayList() {
        return itemsArrayList;
    }

    public void setItemsArrayList(ArrayList<Items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }
}
