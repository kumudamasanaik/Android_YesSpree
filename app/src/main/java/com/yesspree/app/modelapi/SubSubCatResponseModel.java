package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 21-05-2018.
 */

public class SubSubCatResponseModel extends ResponseModel {

    @SerializedName(Constants.RESULT)
    private SubSubCatResponseModel.SubSubCatData subCatData;

    public SubSubCatResponseModel.SubSubCatData getSubCatData() {
        return subCatData;
    }

    public void setSubCatData(SubSubCatResponseModel.SubSubCatData subCatData) {
        this.subCatData = subCatData;
    }


    public static class SubSubCatData {

        @Expose
        @SerializedName(Constants.SUB_CATEGORY)
        private ArrayList<Category> sub_sub_cat_headers;


        @Expose
        @SerializedName(Constants.BANNER_)
        private ArrayList<Banner> bannerArrayList;


        /*  @Expose
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
        private ArrayList<ProductData> topProductList;


        @Expose
        @SerializedName(Constants.BRANDS)
        private ArrayList<Brands> brandsArrayList;




        public ArrayList<Brands> getBrandsArrayList() {
            return brandsArrayList;
        }

        public void setBrandsArrayList(ArrayList<Brands> brandsArrayList) {
            this.brandsArrayList = brandsArrayList;
        }



        public ArrayList<ProductData> getTopProductList() {
            return topProductList;
        }

        public void setTopProductList(ArrayList<ProductData> topProductList) {
            this.topProductList = topProductList;
        }

        public ArrayList<Category> getSub_sub_cat_headers() {
            return sub_sub_cat_headers;
        }

        public void setSub_sub_cat_headers(ArrayList<Category> sub_sub_cat_headers) {
            this.sub_sub_cat_headers = sub_sub_cat_headers;
        }


        public ArrayList<Banner> getBannerArrayList() {
            return bannerArrayList;
        }

        public void setBannerArrayList(ArrayList<Banner> bannerArrayList) {
            this.bannerArrayList = bannerArrayList;
        }

    }


}
