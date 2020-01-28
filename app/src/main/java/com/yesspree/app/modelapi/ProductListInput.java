package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductListInput implements Parcelable {


    @Expose
    @SerializedName("offer")
    private String offer;

    @Expose
    @SerializedName("count")
    private int count = 20;
    @Expose
    @SerializedName("start")
    private int start = 0;
    @Expose
    @SerializedName("sort")
    private String sort;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("brand")
    private String brand;
    @Expose
    @SerializedName("id_subcategory")
    private String id_subcategory;
    @Expose
    @SerializedName("wh_pincode")
    private String wh_pincode;
    @Expose
    @SerializedName("_session")
    private String _session;
    @Expose
    @SerializedName("_id")
    private String _id;

    @SerializedName("search")
    String search;

    @SerializedName("category")
    String category;


    @SerializedName("sku")
    String sku;

    @SerializedName("type")
    String type;


    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId_subcategory() {
        return id_subcategory;
    }

    public void setId_subcategory(String id_subcategory) {
        this.id_subcategory = id_subcategory;
    }

    public String getWh_pincode() {
        return wh_pincode;
    }

    public void setWh_pincode(String wh_pincode) {
        this.wh_pincode = wh_pincode;
    }

    public String get_session() {
        return _session;
    }

    public void set_session(String _session) {
        this._session = _session;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.offer);
        dest.writeInt(this.count);
        dest.writeInt(this.start);
        dest.writeString(this.sort);
        dest.writeString(this.price);
        dest.writeString(this.brand);
        dest.writeString(this.id_subcategory);
        dest.writeString(this.wh_pincode);
        dest.writeString(this._session);
        dest.writeString(this._id);
        dest.writeString(this.search);
        dest.writeString(this.sku);
        dest.writeString(this.category);
        dest.writeString(this.type);
    }

    public ProductListInput() {
    }

    protected ProductListInput(Parcel in) {
        this.offer = in.readString();
        this.count = in.readInt();
        this.start = in.readInt();
        this.sort = in.readString();
        this.price = in.readString();
        this.brand = in.readString();
        this.id_subcategory = in.readString();
        this.wh_pincode = in.readString();
        this._session = in.readString();
        this._id = in.readString();
        this.search = in.readString();
        this.sku = in.readString();
        this.category = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<ProductListInput> CREATOR = new Parcelable.Creator<ProductListInput>() {
        @Override
        public ProductListInput createFromParcel(Parcel source) {
            return new ProductListInput(source);
        }

        @Override
        public ProductListInput[] newArray(int size) {
            return new ProductListInput[size];
        }
    };
}
