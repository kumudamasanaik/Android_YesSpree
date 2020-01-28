package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class RateUsRes extends ResponseModel {
    @SerializedName(Constants.RESULT)
    ArrayList<Result> result;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    class Result {
        @Expose
        @SerializedName("dateadded")
        private String dateadded;
        @Expose
        @SerializedName("is_active")
        private String is_active;
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
        @SerializedName("_id")
        private String _id;


        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

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

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }
}
