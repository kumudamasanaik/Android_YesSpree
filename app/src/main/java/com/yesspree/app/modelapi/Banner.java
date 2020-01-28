package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Banner {

    @Expose
    @SerializedName("bannerdata")
    private ArrayList<Bannerdata> bannerdata;
    @Expose
    @SerializedName("_id")
    private String _id;
    @Expose
    @SerializedName("name")
    private String name;


    public ArrayList<Bannerdata> getBannerdata() {
        return bannerdata;
    }

    public void setBannerdata(ArrayList<Bannerdata> bannerdata) {
        this.bannerdata = bannerdata;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
