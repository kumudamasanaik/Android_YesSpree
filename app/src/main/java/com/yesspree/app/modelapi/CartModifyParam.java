package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartModifyParam {

    @Expose
    @SerializedName("wh_pincode")
    private String wh_pincode;
    @Expose
    @SerializedName("_session")
    private String _session;
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("id_sku")
    private String id_sku;
    @Expose
    @SerializedName("id_product")
    private String id_product;
    @Expose
    @SerializedName("op")
    private String op;
    @Expose
    @SerializedName("_id")
    private String _id;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId_sku() {
        return id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "CartModifyParam{" +
                "wh_pincode='" + wh_pincode + '\'' +
                ", _session='" + _session + '\'' +
                ", quantity='" + quantity + '\'' +
                ", id_sku='" + id_sku + '\'' +
                ", id_product='" + id_product + '\'' +
                ", op='" + op + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
