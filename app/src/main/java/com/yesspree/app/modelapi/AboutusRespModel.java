package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.List;

/**
 * Created by FuGenX-14 on 14-06-2018.
 */

public class AboutusRespModel extends ResponseModel {
    @SerializedName(Constants.RESULT)
    private AboutusRespModel.Data aboutsData;

    public AboutusRespModel.Data getAboutusData() {
        return aboutsData;
    }

    public void setAboutusData(AboutusRespModel.Data aboutsData) {
        this.aboutsData = aboutsData;
    }

    public static class Data {

        @SerializedName(Constants.ABOUT_US)
        @Expose
        private List<Query> aboutus = null;

        @SerializedName(Constants.TERMS_CONDITION)
        @Expose
        private List<Query> termsCondition = null;

        @SerializedName(Constants.PRIVACY_POLICY_JSON)
        @Expose
        private List<Query> privacyPolicy = null;

        public List<Query> getAboutus() {
            return aboutus;
        }

        public void setAboutus(List<Query> aboutus) {
            this.aboutus = aboutus;
        }

        public List<Query> getTermsCondition() {
            return termsCondition;
        }

        public void setTermsCondition(List<Query> termsCondition) {
            this.termsCondition = termsCondition;
        }

        public List<Query> getPrivacyPolicy() {
            return privacyPolicy;
        }

        public void setPrivacyPolicy(List<Query> privacyPolicy) {
            this.privacyPolicy = privacyPolicy;
        }
    }
}
