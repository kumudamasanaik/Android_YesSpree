package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 17-05-2018.
 */

public class SubCategoryResModel extends ResponseModel {

    @SerializedName(Constants.RESULT)
    private SubCatData subCatData;

    @Expose
    @SerializedName(Constants.ADVERTISEMENT)
    ArrayList<Advertisement> advertisement;

    public SubCatData getSubCatData() {
        return subCatData;
    }

    public void setSubCatData(SubCatData subCatData) {
        this.subCatData = subCatData;
    }

    public ArrayList<Advertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(ArrayList<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }


    public static class SubCatData {

        @Expose
        @SerializedName(Constants.SUB_CATEGORY)
        private ArrayList<Category> categoryArrayList;

        @Expose
        @SerializedName(Constants.BANNER_)
        private ArrayList<Banner> bannerArrayList;


        /*@Expose
        @SerializedName(Constants.BANNER_1)
        private ArrayList<Bannerdata> bannerArrayList_1;

        @Expose
        @SerializedName(Constants.BANNER_2)
        private ArrayList<Bannerdata> bannerArrayList_2;


        @Expose
        @SerializedName(Constants.BANNER_3)
        private ArrayList<Bannerdata> bannerArrayList_3;

*/
        @Expose
        @SerializedName(Constants.TOP_PRODUCTS)
        private ArrayList<ProductData> topProductArrayList;

        @Expose
        @SerializedName(Constants.ALL_PRODCUTS)
        private ArrayList<ProductData> allProductArrayList;


        @Expose
        @SerializedName(Constants.BRANDS)
        private ArrayList<Brands> brandsArrayList;


        public ArrayList<Category> getCategoryArrayList() {
            return categoryArrayList;
        }

        public void setCategoryArrayList(ArrayList<Category> categoryArrayList) {
            this.categoryArrayList = categoryArrayList;
        }


        public ArrayList<ProductData> getTopProductArrayList() {
            return topProductArrayList;
        }

        public void setTopProductArrayList(ArrayList<ProductData> topProductArrayList) {
            this.topProductArrayList = topProductArrayList;
        }

        public ArrayList<ProductData> getAllProductArrayList() {
            return allProductArrayList;
        }

        public void setAllProductArrayList(ArrayList<ProductData> allProductArrayList) {
            this.allProductArrayList = allProductArrayList;
        }

        public ArrayList<Brands> getBrandsArrayList() {
            return brandsArrayList;
        }

        public void setBrandsArrayList(ArrayList<Brands> brandsArrayList) {
            this.brandsArrayList = brandsArrayList;
        }

        public ArrayList<Banner> getBannerArrayList() {
            return bannerArrayList;
        }

        public void setBannerArrayList(ArrayList<Banner> bannerArrayList) {
            this.bannerArrayList = bannerArrayList;
        }


    }

}