package com.yesspree.app.screens.mysubscription.active_fragment;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public interface IActivePresenter {

    void getSubscritionList(String header ,String type);
    void cancelSubscrition(String orderNum);

}
