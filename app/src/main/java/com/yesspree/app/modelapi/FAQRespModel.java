package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FuGenX-14 on 18-06-2018.
 */

public class FAQRespModel extends ResponseModel {

    @SerializedName(Constants.RESULT)
    ArrayList<MainQuestion> mainQuestionArrayList;

    public ArrayList<MainQuestion> getMainQuestion() {
        return mainQuestionArrayList;
    }

    public void setMainQuestion(ArrayList<MainQuestion> mainQuestionArrayList) {
        this.mainQuestionArrayList = mainQuestionArrayList;
    }

    public static class MainQuestion {

        @SerializedName("category")
        @Expose
        private String mainQuestionName;

        @SerializedName("faq")
        @Expose
        private List<SubQuestion> subQuestionList = null;


        public String getMainQuestionName() {
            return mainQuestionName;
        }

        public void setMainQuestionName(String mainQuestionName) {
            this.mainQuestionName = mainQuestionName;
        }

        public List<SubQuestion> getSubQuestionList() {
            return subQuestionList;
        }

        public void setSubQuestionList(List<SubQuestion> subQuestionList) {
            this.subQuestionList = subQuestionList;
        }
    }
}
