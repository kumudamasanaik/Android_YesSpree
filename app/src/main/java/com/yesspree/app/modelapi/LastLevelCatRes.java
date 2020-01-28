package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class LastLevelCatRes extends ResponseModel {
    @SerializedName(Constants.RESULT)
    private Res result;

    public Res getResult() {
        return result;
    }

    public void setResult(Res result) {
        this.result = result;
    }

    public static class Res {

        @Expose
        @SerializedName(Constants.BANNER_)
        private ArrayList<Banner> bannerArrayList;

        @Expose
        @SerializedName(Constants.CATEGORY)
        private ArrayList<Category> category;
        @Expose
        @SerializedName(Constants.BRANDS)
        private ArrayList<Brands> brands;


        public ArrayList<Category> getCategory() {
            return category;
        }

        public void setCategory(ArrayList<Category> category) {
            this.category = category;
        }

        public ArrayList<Brands> getBrands() {
            return brands;
        }

        public void setBrands(ArrayList<Brands> brands) {
            this.brands = brands;
        }


        public ArrayList<Banner> getBannerArrayList() {
            return bannerArrayList;
        }

        public void setBannerArrayList(ArrayList<Banner> bannerArrayList) {
            this.bannerArrayList = bannerArrayList;
        }
    }
}
