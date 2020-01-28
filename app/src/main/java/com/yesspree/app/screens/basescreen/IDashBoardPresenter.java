package com.yesspree.app.screens.basescreen;

import android.content.Context;

import com.yesspree.app.modelapi.RateUsInputModel;

public interface IDashBoardPresenter {
    void sendFirebaseTokenToServer(Context cntxt);
    void submitRatings(RateUsInputModel rateUsInputModel);
}
