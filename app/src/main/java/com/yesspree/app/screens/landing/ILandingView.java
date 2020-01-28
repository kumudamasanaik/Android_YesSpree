package com.yesspree.app.screens.landing;

import com.yesspree.app.modelapi.UserData;
import com.yesspree.app.screens.common.IBaseView;

public interface ILandingView extends IBaseView {

    void navigateToHomeScreen();

    void navigateToLoginScreen();

    void navigateSignUpScreen();

    void callSocialSignInApi();

    void socailLoginRes(UserData userData);
}
