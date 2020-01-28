package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class DashboardResModel extends ResponseModel {

    @SerializedName(Constants.RESULT)
    private DashboardData dashboardData;

    @Expose
    @SerializedName(Constants.SUMMARY)
    CartSummaryData cartSummaryData;

    @Expose
    @SerializedName(Constants.REFER)
    Refferal refferal;

    @Expose
    @SerializedName(Constants.ADVERTISEMENT)
    ArrayList<Advertisement> advertisement;

    @Expose
    @SerializedName(Constants.CONTACT_US)
    CustomerCare customerCareData;

    public CustomerCare getCustomerCareData() {
        return customerCareData;
    }

    public void setCustomerCareData(CustomerCare customerCare) {
        this.customerCareData = customerCare;
    }

    public DashboardData getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(DashboardData dashboardData) {
        this.dashboardData = dashboardData;
    }

    public CartSummaryData getCartSummaryData() {
        return cartSummaryData;
    }

    public void setCartSummaryData(CartSummaryData cartSummaryData) {
        this.cartSummaryData = cartSummaryData;
    }

    public Refferal getRefferal() {
        return refferal;
    }

    public void setRefferal(Refferal refferal) {
        this.refferal = refferal;
    }

    public ArrayList<Advertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(ArrayList<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }

    public static class DashboardData {
        @Expose
        @SerializedName(Constants.BRANDS)
        private ArrayList<Brands> brands;
        @Expose
        @SerializedName(Constants.SPECIFIC_PRODUCT)
        private ArrayList<SpecificProducts> specificProdList;
        @Expose
        @SerializedName(Constants.SAVINGS)
        private ArrayList<Savings> savings;
        @Expose
        @SerializedName(Constants.BANNNER)
        private ArrayList<Banner> banner;
        @Expose
        @SerializedName(Constants.CATEGORY)
        private ArrayList<Category> category;
        @Expose
        @SerializedName(Constants.CUSTOMER)
        ArrayList<CustomerData> customerDataArrayList;


        public ArrayList<Brands> getBrands() {
            return brands;
        }

        public void setBrands(ArrayList<Brands> brands) {
            this.brands = brands;
        }

        public ArrayList<SpecificProducts> getSpecificProdList() {
            return specificProdList;
        }

        public void setSpecificProdList(ArrayList<SpecificProducts> specificProdList) {
            this.specificProdList = specificProdList;
        }

        public ArrayList<Savings> getSavings() {
            return savings;
        }

        public void setSavings(ArrayList<Savings> savings) {
            this.savings = savings;
        }

        public ArrayList<Banner> getBanner() {
            return banner;
        }

        public void setBanner(ArrayList<Banner> banner) {
            this.banner = banner;
        }

        public ArrayList<Category> getCategory() {
            return category;
        }

        public void setCategory(ArrayList<Category> category) {
            this.category = category;
        }

        public ArrayList<CustomerData> getCustomerDataArrayList() {
            return customerDataArrayList;
        }

        public void setCustomerDataArrayList(ArrayList<CustomerData> customerDataArrayList) {
            this.customerDataArrayList = customerDataArrayList;
        }

    }
}
