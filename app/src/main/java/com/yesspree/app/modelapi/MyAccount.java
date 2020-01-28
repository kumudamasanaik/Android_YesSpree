package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 22-06-2018.
 */

public class MyAccount {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("person_prefix")
    @Expose
    private String personPrefix;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("verified")
    @Expose
    private String verified;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("customer_type")
    @Expose
    private String customerType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthday")
    @Expose
    private String dateOfbirth;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("newsletter")
    @Expose
    private String newsletter;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email_verified")
    @Expose
    private String emailVerified;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("referred_code")
    @Expose
    private String referredCode;
    @SerializedName("referred_by")
    @Expose
    private String referredBy;
    @SerializedName("referral_used")
    @Expose
    private String referralUsed;
    @SerializedName("referred_amount")
    @Expose
    private String referredAmount;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("wallet")
    @Expose
    private String wallet;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonPrefix() {
        return personPrefix;
    }

    public void setPersonPrefix(String personPrefix) {
        this.personPrefix = personPrefix;
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

    public String getDateOfBirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(String birthday) {
        this.dateOfbirth = birthday;
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

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getReferredCode() {
        return referredCode;
    }

    public void setReferredCode(String referredCode) {
        this.referredCode = referredCode;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getReferralUsed() {
        return referralUsed;
    }

    public void setReferralUsed(String referralUsed) {
        this.referralUsed = referralUsed;
    }

    public String getReferredAmount() {
        return referredAmount;
    }

    public void setReferredAmount(String referredAmount) {
        this.referredAmount = referredAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }


}

