package com.yesspree.app.screens.landing;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

public class SocialSignInputModel {

    @Expose
    @SerializedName(Constants.MOBILE)
    private String mobile;
    @Expose
    @SerializedName(Constants.EMAIL)
    private String email;
    @Expose
    @SerializedName(Constants.LAST_NAME)
    private String last_name;
    @Expose
    @SerializedName(Constants.FIRST_NAME)
    private String first_name;
    @Expose
    @SerializedName(Constants.GOOGLE_ID)
    private String google_id;
    @Expose
    @SerializedName(Constants.FACEBOOK_ID)
    private String facebook_id;

    @Expose
    @SerializedName(Constants.SOCIALTYPE)
    private String socialType;
    private boolean emailVerified;
    private String socId;

    Uri photoUrl;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }
}
