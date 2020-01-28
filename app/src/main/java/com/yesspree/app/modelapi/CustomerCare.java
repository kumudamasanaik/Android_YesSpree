package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 29-06-2018.
 */

public class CustomerCare implements Parcelable {

    @Expose
    @SerializedName("customercare_email")
    private String customer_e_mail;
    @Expose
    @SerializedName("customercare_mobile")
    private String customer_mobile;

    public String getCustomer_e_mail() {
        return customer_e_mail;
    }

    public void setCustomer_e_mail(String customer_e_mail) {
        this.customer_e_mail = customer_e_mail;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customer_e_mail);
        dest.writeString(this.customer_mobile);
    }

    public CustomerCare() {
    }

    protected CustomerCare(Parcel in) {
        this.customer_e_mail = in.readString();
        this.customer_mobile = in.readString();
    }

    public static final Parcelable.Creator<CustomerCare> CREATOR = new Parcelable.Creator<CustomerCare>() {
        @Override
        public CustomerCare createFromParcel(Parcel source) {
            return new CustomerCare(source);
        }

        @Override
        public CustomerCare[] newArray(int size) {
            return new CustomerCare[size];
        }
    };
}
