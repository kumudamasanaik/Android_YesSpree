package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class SearchProdRes extends ResponseModel{
    @SerializedName(Constants.PRODUCTS)
    ArrayList<Products> productsArrayList;

    @SerializedName(Constants.SUMMARY)
    CartSummaryData summaryData;

    public ArrayList<Products> getProductsArrayList() {
        return productsArrayList;
    }

    public void setProductsArrayList(ArrayList<Products> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }

    public CartSummaryData getSummaryData() {
        return summaryData;
    }

    public void setSummaryData(CartSummaryData summaryData) {
        this.summaryData = summaryData;
    }

    public static class Products {
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("_id")
        private String _id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }
}
