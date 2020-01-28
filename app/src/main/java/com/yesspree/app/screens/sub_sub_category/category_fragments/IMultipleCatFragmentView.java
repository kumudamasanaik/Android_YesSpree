package com.yesspree.app.screens.sub_sub_category.category_fragments;

import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 09-05-2018.
 */

public interface IMultipleCatFragmentView extends IBaseView {
    void setCategoryApiRes(MultipleCatRespModel dashboardApiRes);
    void showViewState(int state);

}
