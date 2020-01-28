package com.yesspree.app.screens.filters;

import com.yesspree.app.modelapi.FilterTypeRes;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 30-04-2018.
 */

public interface IFilterView extends IBaseView {
    void setupTabLayout();

    void getFilterType();

    void setFilterTypeRes(FilterTypeRes res);

    void showViewState(int state);
}
