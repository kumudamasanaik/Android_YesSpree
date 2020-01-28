package com.yesspree.app.screens.signup;

import com.yesspree.app.modelapi.SignupRes;

public interface ISignupView {

    void init();

    void signUp();

    void message(String string);

    void signupRes(SignupRes res);

    void showLoader();

    void hideLoader();

}
