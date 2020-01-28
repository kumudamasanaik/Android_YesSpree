package com.yesspree.app.screens.signup;

public interface ISignupPresenter {
    boolean validate(SignUpModelToValidation signUp);

    void doSignUp(SignUpModelToValidation modelToValidation);
}
