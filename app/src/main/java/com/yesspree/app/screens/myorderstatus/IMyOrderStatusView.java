package com.yesspree.app.screens.myorderstatus;

import com.yesspree.app.modelapi.MyOrderStatusRes;
import com.yesspree.app.screens.common.IBaseView;

public interface IMyOrderStatusView extends IBaseView {

    void callMyOrderStatusApi();

    void setMyOrderRes(MyOrderStatusRes res);

    void showViewState(int state);

}
