package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateUsInputModel {

    @Expose
    @SerializedName("review")
    private String review;
    @Expose
    @SerializedName("rating")
    private String rating;
    @Expose
    @SerializedName("id_customer")
    private String id_customer;
    @Expose
    @SerializedName("table")
    private String table = "ratings_reviews";
    @Expose
    @SerializedName("op")
    private String op = "create";


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
