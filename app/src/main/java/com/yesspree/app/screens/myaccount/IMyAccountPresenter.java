package com.yesspree.app.screens.myaccount;

import com.yesspree.app.screens.signup.SignUpModelToValidation;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public interface IMyAccountPresenter {
    void callMyAcountDetails(String authKey);
    void saveUpdatedAccountDetails(SignUpModelToValidation signUpModelToValidation);
}
