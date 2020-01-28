package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sub {

    @Expose
    @SerializedName("sub")
    private List<Sub> sub;
    @Expose
    @SerializedName("updated")
    private String updated;
    @Expose
    @SerializedName("block")
    private String block;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("pic")
    private String pic;
    @Expose
    @SerializedName("parent")
    private String parent;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("_id")
    private String _id;

    public List<Sub> getSub() {
        return sub;
    }



    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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
}
