package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sku implements Parcelable {
    @Expose
    @SerializedName("mycart")
    private String mycart;
    @Expose
    @SerializedName("selling_price")
    private int selling_price;
    @Expose
    @SerializedName("stock")
    private String stock;
    @Expose
    @SerializedName("min_quantity")
    private String min_quantity;
    @Expose
    @SerializedName("mrp")
    private String mrp;
    @Expose
    @SerializedName("realization")
    private String realization;
    @Expose
    @SerializedName("tax")
    private String tax;
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("sku")
    private String sku;
    @Expose
    @SerializedName("_id")
    private String _id;
    @Expose
    @SerializedName("id_product")
    private String id_product;

    private int tempMyCart = -1;


    String selectedId = "0";


    public int getMycart() {
        return Integer.parseInt(mycart);
    }

    public void setMycart(String mycart) {
        this.mycart = mycart;
    }

    public int getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(int selling_price) {
        this.selling_price = selling_price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getMin_quantity() {
        return Integer.parseInt(min_quantity);
    }

    public void setMin_quantity(String min_quantity) {
        this.min_quantity = min_quantity;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getRealization() {
        return realization;
    }

    public void setRealization(String realization) {
        this.realization = realization;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public int getTempMyCart() {
        return tempMyCart;
    }

    public void setTempMyCart(int tempMyCart) {
        this.tempMyCart = tempMyCart;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "mycart='" + mycart + '\'' +
                ", size='" + size + '\'' +
                ", sku='" + sku + '\'' +
                ", _id='" + _id + '\'' +
                ", tempMyCart=" + tempMyCart +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mycart);
        dest.writeInt(this.selling_price);
        dest.writeString(this.stock);
        dest.writeString(this.min_quantity);
        dest.writeString(this.mrp);
        dest.writeString(this.realization);
        dest.writeString(this.tax);
        dest.writeString(this.size);
        dest.writeString(this.sku);
        dest.writeString(this._id);
        dest.writeString(this.id_product);
        dest.writeInt(this.tempMyCart);
        dest.writeString(this.selectedId);
    }

    public Sku() {
    }

    protected Sku(Parcel in) {
        this.mycart = in.readString();
        this.selling_price = in.readInt();
        this.stock = in.readString();
        this.min_quantity = in.readString();
        this.mrp = in.readString();
        this.realization = in.readString();
        this.tax = in.readString();
        this.size = in.readString();
        this.sku = in.readString();
        this._id = in.readString();
        this.id_product = in.readString();
        this.tempMyCart = in.readInt();
        this.selectedId = in.readString();
    }

    public static final Parcelable.Creator<Sku> CREATOR = new Parcelable.Creator<Sku>() {
        @Override
        public Sku createFromParcel(Parcel source) {
            return new Sku(source);
        }

        @Override
        public Sku[] newArray(int size) {
            return new Sku[size];
        }
    };
}
