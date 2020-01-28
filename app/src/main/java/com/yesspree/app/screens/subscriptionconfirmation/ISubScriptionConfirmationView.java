package com.yesspree.app.screens.subscriptionconfirmation;

import com.yesspree.app.modelapi.SubscribeProdRes;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 05-07-2018.
 */

public interface ISubScriptionConfirmationView extends IBaseView {

    public void playMuzic();

    public void showViewState(int state);

    public void callSubScriptionConfirmApi();

    public void setSubScriptionConfirmAPiResp(SubscribeProdRes res);
}
