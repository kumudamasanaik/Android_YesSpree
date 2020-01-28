package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 27-06-2018.
 */

public class TermsCondition implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("id_offer")
    @Expose
    private String idOffer;
    @SerializedName("value")
    @Expose
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.idOffer);
        dest.writeString(this.value);
    }

    public TermsCondition() {
    }

    protected TermsCondition(Parcel in) {
        this.id = in.readString();
        this.idOffer = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<TermsCondition> CREATOR = new Parcelable.Creator<TermsCondition>() {
        @Override
        public TermsCondition createFromParcel(Parcel source) {
            return new TermsCondition(source);
        }

        @Override
        public TermsCondition[] newArray(int size) {
            return new TermsCondition[size];
        }
    };
}

