package com.yesspree.app.screens.myorder;

import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 08-06-2018.
 */

public interface IMyOrdersView extends IBaseView {
    void getMyOrdersList();

    void setMyOrderListRes(MyOrdersRespModel responce);

    void setCancelOrderRes(CancelOrder responce);

    void showViewState(int state);

}
