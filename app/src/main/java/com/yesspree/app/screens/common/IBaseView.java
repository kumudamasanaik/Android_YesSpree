package com.yesspree.app.screens.common;

public interface IBaseView {

    void init();

    void showMsg(String msg);

    void showLoader(String msg);

    void hideLoader();
}
