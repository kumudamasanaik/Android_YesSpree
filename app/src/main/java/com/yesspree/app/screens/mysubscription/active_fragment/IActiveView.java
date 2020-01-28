package com.yesspree.app.screens.mysubscription.active_fragment;
import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public interface IActiveView extends IBaseView {

    void callMySubscrApi();
    void setMySubscrRes(MyOrdersRespModel res);
    void setCancelSubscrRes(CancelOrder res);
    void showViewState(int state);
}
