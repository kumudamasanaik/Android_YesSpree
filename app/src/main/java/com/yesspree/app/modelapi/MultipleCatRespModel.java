package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.List;

/**
 * Created by FuGenX-14 on 23-05-2018.
 */

public class MultipleCatRespModel extends ResponseModel {

    @SerializedName(Constants.RESULT)
    private MultipleCatRespModel.ChildCategory subCatData;

    public MultipleCatRespModel.ChildCategory getChildcat() {
        return subCatData;
    }

    public void setChildCat(MultipleCatRespModel.ChildCategory subCatData) {
        this.subCatData = subCatData;
    }


    public static class ChildCategory {

        @SerializedName(Constants.CATEGORY)
        @Expose
        private List<Category> category = null;

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
        }
    }
}
