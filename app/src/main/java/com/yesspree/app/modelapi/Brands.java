package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brands {

    @Expose
    @SerializedName("pic")
    private String pic;
    @Expose
    @SerializedName("brand_en")
    private String brand_en;
    @Expose
    @SerializedName("_id")
    private String _id;


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBrand_en() {
        return brand_en;
    }

    public void setBrand_en(String brand_en) {
        this.brand_en = brand_en;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
