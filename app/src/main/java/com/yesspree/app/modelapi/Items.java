package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items {


    @Expose
    @SerializedName("category")
    private String category;
    @Expose
    @SerializedName("category_id")
    private String category_id;
    @Expose
    @SerializedName("id_category")
    private String id_category;
    @Expose
    @SerializedName("brand")
    private String brand;
    @Expose
    @SerializedName("tags")
    private String tags;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("article_no")
    private String article_no;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("_id")
    private String _id;
    @Expose
    @SerializedName("pic")
    private ArrayList<Pic> pic;



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticle_no() {
        return article_no;
    }

    public void setArticle_no(String article_no) {
        this.article_no = article_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<Pic> getPic() {
        return pic;
    }

    public void setPic(ArrayList<Pic> pic) {
        this.pic = pic;
    }

}
