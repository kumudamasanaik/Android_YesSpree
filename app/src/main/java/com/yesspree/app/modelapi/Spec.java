package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spec implements Parcelable {

    @Expose
    @SerializedName("value")
    private String value;
    @Expose
    @SerializedName("specification")
    private String specification;
    @Expose
    @SerializedName("id_product")
    private String id_product;
    @Expose
    @SerializedName("_id")
    private String _id;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeString(this.specification);
        dest.writeString(this.id_product);
        dest.writeString(this._id);
    }

    public Spec() {
    }

    protected Spec(Parcel in) {
        this.value = in.readString();
        this.specification = in.readString();
        this.id_product = in.readString();
        this._id = in.readString();
    }

    public static final Parcelable.Creator<Spec> CREATOR = new Parcelable.Creator<Spec>() {
        @Override
        public Spec createFromParcel(Parcel source) {
            return new Spec(source);
        }

        @Override
        public Spec[] newArray(int size) {
            return new Spec[size];
        }
    };
}
