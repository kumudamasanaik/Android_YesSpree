package com.yesspree.app.screens.offers;

import com.yesspree.app.modelapi.OffersResModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 02-05-2018.
 */

public interface IOffersView extends IBaseView {
    void getOffersData();
    void setOffersResp(OffersResModel responseModel);
    void showViewState(int state);
}
