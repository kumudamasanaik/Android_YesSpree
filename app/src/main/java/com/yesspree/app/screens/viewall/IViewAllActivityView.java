package com.yesspree.app.screens.viewall;

import com.yesspree.app.modelapi.ViewAllBrandsRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 10-07-2018.
 */

public interface IViewAllActivityView extends IBaseView {
    void callViewAllBrandsApi();
    void setViewAllBrandsApiResp(ViewAllBrandsRespModel resp);
    void showViewState(int state);

}
