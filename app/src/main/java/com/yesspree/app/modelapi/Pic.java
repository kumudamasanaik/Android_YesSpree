package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pic implements Parcelable {

    @Expose
    @SerializedName("id_product")
    private String id_product;
    @Expose
    @SerializedName("pic_thumb")
    private String pic_thumb;
    @Expose
    @SerializedName("pic")
    private String pic;
    @Expose
    @SerializedName("_id")
    private String _id;

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getPic_thumb() {
        return pic_thumb;
    }

    public void setPic_thumb(String pic_thumb) {
        this.pic_thumb = pic_thumb;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_product);
        dest.writeString(this.pic_thumb);
        dest.writeString(this.pic);
        dest.writeString(this._id);
    }

    public Pic() {
    }

    protected Pic(Parcel in) {
        this.id_product = in.readString();
        this.pic_thumb = in.readString();
        this.pic = in.readString();
        this._id = in.readString();
    }

    public static final Parcelable.Creator<Pic> CREATOR = new Parcelable.Creator<Pic>() {
        @Override
        public Pic createFromParcel(Parcel source) {
            return new Pic(source);
        }

        @Override
        public Pic[] newArray(int size) {
            return new Pic[size];
        }
    };
}
