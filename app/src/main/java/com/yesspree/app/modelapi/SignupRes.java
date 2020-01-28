package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignupRes extends ResponseModel {
    @SerializedName("result")
    List<SignupResult> signupResult;

    public List<SignupResult> getSignupResult() {
        return signupResult;
    }

    public void setSignupResult(List<SignupResult> signupResult) {
        this.signupResult = signupResult;
    }

    public static class SignupResult implements Parcelable {

        @Expose
        @SerializedName("referral_used")
        public String referral_used;
        @Expose
        @SerializedName("referred_by")
        public String referred_by;
        @Expose
        @SerializedName("referral_code")
        public String referral_code;
        @Expose
        @SerializedName("email_verified")
        public String email_verified;
        @Expose
        @SerializedName("newsletter")
        public String newsletter;
        @Expose
        @SerializedName("gender")
        public String gender;
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("customer_type")
        public String customer_type;
        @Expose
        @SerializedName("linkedin_id")
        public String linkedin_id;
        @Expose
        @SerializedName("google_id")
        public String google_id;
        @Expose
        @SerializedName("facebook_id")
        public String facebook_id;
        @Expose
        @SerializedName("pic")
        public String pic;
        @Expose
        @SerializedName("verified")
        public String verified;
        @Expose
        @SerializedName("otp")
        public String otp;
        @Expose
        @SerializedName("mobile")
        public String mobile;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("last_name")
        public String last_name;
        @Expose
        @SerializedName("first_name")
        public String first_name;
        @Expose
        @SerializedName("_id")
        public String _id;

        public String getReferral_used() {
            return referral_used;
        }

        public void setReferral_used(String referral_used) {
            this.referral_used = referral_used;
        }

        public String getReferred_by() {
            return referred_by;
        }

        public void setReferred_by(String referred_by) {
            this.referred_by = referred_by;
        }

        public String getReferral_code() {
            return referral_code;
        }

        public void setReferral_code(String referral_code) {
            this.referral_code = referral_code;
        }

        public String getEmail_verified() {
            return email_verified;
        }

        public void setEmail_verified(String email_verified) {
            this.email_verified = email_verified;
        }

        public String getNewsletter() {
            return newsletter;
        }

        public void setNewsletter(String newsletter) {
            this.newsletter = newsletter;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCustomer_type() {
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public String getLinkedin_id() {
            return linkedin_id;
        }

        public void setLinkedin_id(String linkedin_id) {
            this.linkedin_id = linkedin_id;
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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

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
            dest.writeString(this.referral_used);
            dest.writeString(this.referred_by);
            dest.writeString(this.referral_code);
            dest.writeString(this.email_verified);
            dest.writeString(this.newsletter);
            dest.writeString(this.gender);
            dest.writeString(this.status);
            dest.writeString(this.customer_type);
            dest.writeString(this.linkedin_id);
            dest.writeString(this.google_id);
            dest.writeString(this.facebook_id);
            dest.writeString(this.pic);
            dest.writeString(this.verified);
            dest.writeString(this.otp);
            dest.writeString(this.mobile);
            dest.writeString(this.email);
            dest.writeString(this.last_name);
            dest.writeString(this.first_name);
            dest.writeString(this._id);
        }

        public SignupResult() {
        }

        protected SignupResult(Parcel in) {
            this.referral_used = in.readString();
            this.referred_by = in.readString();
            this.referral_code = in.readString();
            this.email_verified = in.readString();
            this.newsletter = in.readString();
            this.gender = in.readString();
            this.status = in.readString();
            this.customer_type = in.readString();
            this.linkedin_id = in.readString();
            this.google_id = in.readString();
            this.facebook_id = in.readString();
            this.pic = in.readString();
            this.verified = in.readString();
            this.otp = in.readString();
            this.mobile = in.readString();
            this.email = in.readString();
            this.last_name = in.readString();
            this.first_name = in.readString();
            this._id = in.readString();
        }

        public static final Parcelable.Creator<SignupResult> CREATOR = new Parcelable.Creator<SignupResult>() {
            @Override
            public SignupResult createFromParcel(Parcel source) {
                return new SignupResult(source);
            }

            @Override
            public SignupResult[] newArray(int size) {
                return new SignupResult[size];
            }
        };
    }


}
