package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 27-06-2018.
 */

public class Offers implements Parcelable {
    @SerializedName("_id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("long_desc")
    @Expose
    private String longDesc;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("target_type")
    @Expose
    private String targetType;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("terms_condition")
    @Expose
    private ArrayList<TermsCondition> termsCondition = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public ArrayList<TermsCondition> getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(ArrayList<TermsCondition> termsCondition) {
        this.termsCondition = termsCondition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.shortDesc);
        dest.writeString(this.longDesc);
        dest.writeString(this.pic);
        dest.writeString(this.promoCode);
        dest.writeString(this.targetType);
        dest.writeString(this.target);
        dest.writeString(this.updated);
        dest.writeList(this.termsCondition);
    }

    public Offers() {
    }

    protected Offers(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.shortDesc = in.readString();
        this.longDesc = in.readString();
        this.pic = in.readString();
        this.promoCode = in.readString();
        this.targetType = in.readString();
        this.target = in.readString();
        this.updated = in.readString();
        this.termsCondition = new ArrayList<TermsCondition>();
        in.readList(this.termsCondition, TermsCondition.class.getClassLoader());
    }

    public static final Parcelable.Creator<Offers> CREATOR = new Parcelable.Creator<Offers>() {
        @Override
        public Offers createFromParcel(Parcel source) {
            return new Offers(source);
        }

        @Override
        public Offers[] newArray(int size) {
            return new Offers[size];
        }
    };
}
