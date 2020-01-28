package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CustomerData implements Parcelable {

    public static final Creator<CustomerData> CREATOR = new Creator<CustomerData>() {
        @Override
        public CustomerData createFromParcel(Parcel source) {
            return new CustomerData(source);
        }

        @Override
        public CustomerData[] newArray(int size) {
            return new CustomerData[size];
        }
    };
    @SerializedName("_id")
    private String id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String mobile;
    private String otp;
    private String verified;
    private String pic;
    @SerializedName("facebook_id")
    private String facebookId;
    @SerializedName("google_id")
    private String googleId;
    @SerializedName("linkedin_id")
    private String linkedinId;
    @SerializedName("customer_type")
    private String customerType;
    private String status;
    private String gender;
    private String birthday;
    private String newsletter;
    private String website;
    private String note;
    @SerializedName("email_verified")
    private String emailVerified;
    @SerializedName("referral_code")
    private String referralCode;
    @SerializedName("referred_by")
    private String referredBy;
    @SerializedName("referral_used")
    private String referredUsed;

    @SerializedName("person_prefix")
    private String person_prefix;

    public CustomerData() {
    }

    protected CustomerData(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.otp = in.readString();
        this.verified = in.readString();
        this.pic = in.readString();
        this.facebookId = in.readString();
        this.googleId = in.readString();
        this.linkedinId = in.readString();
        this.customerType = in.readString();
        this.status = in.readString();
        this.gender = in.readString();
        this.birthday = in.readString();
        this.newsletter = in.readString();
        this.website = in.readString();
        this.note = in.readString();
        this.emailVerified = in.readString();
        this.referralCode = in.readString();
        this.referredBy = in.readString();
        this.referredUsed = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getLinkedinId() {
        return linkedinId;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getReferredUsed() {
        return referredUsed;
    }

    public void setReferredUsed(String referredUsed) {
        this.referredUsed = referredUsed;
    }

    public String getPerson_prefix() {
        return person_prefix;
    }

    public void setPerson_prefix(String person_prefix) {
        this.person_prefix = person_prefix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.otp);
        dest.writeString(this.verified);
        dest.writeString(this.pic);
        dest.writeString(this.facebookId);
        dest.writeString(this.googleId);
        dest.writeString(this.linkedinId);
        dest.writeString(this.customerType);
        dest.writeString(this.status);
        dest.writeString(this.gender);
        dest.writeString(this.birthday);
        dest.writeString(this.newsletter);
        dest.writeString(this.website);
        dest.writeString(this.note);
        dest.writeString(this.emailVerified);
        dest.writeString(this.referralCode);
        dest.writeString(this.referredBy);
        dest.writeString(this.referredUsed);
    }
}
