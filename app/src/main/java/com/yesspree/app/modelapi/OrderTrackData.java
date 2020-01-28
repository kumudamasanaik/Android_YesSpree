package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderTrackData {

    @Expose
    @SerializedName("updated")
    private String updated;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id_order")
    private String id_order;
    @Expose
    @SerializedName("_id")
    private String _id;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
