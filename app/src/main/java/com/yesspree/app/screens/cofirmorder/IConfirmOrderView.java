package com.yesspree.app.screens.cofirmorder;

import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.screens.common.IBaseView;

public interface IConfirmOrderView extends IBaseView {

    void fetchOrderSummeryApi();

    void setOrderSummeryRes(FetchCartSummaryResponse res);

    void showViewState(int state);
}
