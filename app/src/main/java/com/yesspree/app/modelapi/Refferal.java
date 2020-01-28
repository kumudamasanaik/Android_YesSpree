package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 28-06-2018.
 */

public class Refferal implements Parcelable {
    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("link")
    private String referal_link;

    @Expose
    @SerializedName("ref_code")
    private String refer_code;

    @Expose
    @SerializedName("share_message")
    private String share_msg;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }

    public String getShare_msg() {
        return share_msg;
    }

    public void setShare_msg(String share_msg) {
        this.share_msg = share_msg;
    }

    public String getReferal_link() {
        return referal_link;
    }

    public void setReferal_link(String referal_link) {
        this.referal_link = referal_link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.refer_code);
        dest.writeString(this.share_msg);
        dest.writeString(this.referal_link);
    }

    public Refferal() {
    }

    protected Refferal(Parcel in) {
        this.image = in.readString();
        this.refer_code = in.readString();
        this.share_msg = in.readString();
        this.referal_link = in.readString();
    }

    public static final Parcelable.Creator<Refferal> CREATOR = new Parcelable.Creator<Refferal>() {
        @Override
        public Refferal createFromParcel(Parcel source) {
            return new Refferal(source);
        }

        @Override
        public Refferal[] newArray(int size) {
            return new Refferal[size];
        }
    };
}
