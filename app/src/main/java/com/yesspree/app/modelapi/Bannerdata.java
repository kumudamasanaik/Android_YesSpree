package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bannerdata {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("title")
    private String title;


    @Expose
    @SerializedName("target")
    private String target;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("type_id")
    private String type_id;
    @Expose
    @SerializedName("pic")
    private String pic;
    @Expose
    @SerializedName("_id")
    private String _id;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
