package com.yesspree.app.screens.catlastscreen;

import com.yesspree.app.modelapi.LastLevelCatRes;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.screens.common.IBaseView;

public interface ILastLevCatView extends IBaseView {

    void callLastLevelCatApi();
    void callChildCatagoryData(String childCatID);

    void showViewState(int state);

    void setLastLevelCatApiResponce(LastLevelCatRes res);
    void setChildCatApiResponce(MultipleCatRespModel res);
}
