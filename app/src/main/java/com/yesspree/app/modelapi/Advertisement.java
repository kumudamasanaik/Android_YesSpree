package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 03-07-2018.
 */

public class Advertisement {

    @Expose
    @SerializedName("_id")
    private String id;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("pic")
    private String pic;

    @Expose
    @SerializedName("clicked")
    private String clicked;

    @Expose
    @SerializedName("sort")
    private String sort;

    @Expose
    @SerializedName("type")
    private String screen_type;

    @Expose
    @SerializedName("validity_start")
    private String validity_start;

    @Expose
    @SerializedName("validity_end")
    private String validity_end;

    @Expose
    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getClicked() {
        return clicked;
    }

    public void setClicked(String clicked) {
        this.clicked = clicked;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getScreen_type() {
        return screen_type;
    }

    public void setScreen_type(String screen_type) {
        this.screen_type = screen_type;
    }

    public String getValidity_start() {
        return validity_start;
    }

    public void setValidity_start(String validity_start) {
        this.validity_start = validity_start;
    }

    public String getValidity_end() {
        return validity_end;
    }

    public void setValidity_end(String validity_end) {
        this.validity_end = validity_end;
    }
}
