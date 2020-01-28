package com.yesspree.app.screens.signup;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.SignupRes;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;
import com.yesspree.app.utility.Validation;

import retrofit2.Call;
import retrofit2.Response;

public class SignupPresenter implements ISignupPresenter, IResponseInterface {

    private final ISignupView view;
    IRequestInterface requestInterface;


    public SignupPresenter(ISignupView view) {
        this.view = view;
        requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public boolean validate(SignUpModelToValidation signUp) {

        if (!Validation.isValidString(signUp.person_prefix)) {
            view.message("9");
            return false;
        }
        if (!Validation.isValidString(signUp.first_name)) {
            view.message("1");
            return false;
        }
        if (!Validation.isValidString(signUp.last_name)) {
            view.message("2");
            return false;
        }
        if (!Validation.isValidString(signUp.email)) {
            view.message("3");
            return false;
        }
        if (!Validation.isValidEmail(signUp.email)) {
            view.message("8");
            return false;
        }

        if (!Validation.isValidString(signUp.mobile)) {
            view.message("4");
            return false;
        }
        if (!Validation.isValidString(signUp.password)) {
            view.message("5");
            return false;
        }
        if (!Validation.isValidString(signUp.confirmPass)) {
            view.message("6");
            return false;
        }
        if (!signUp.password.equals(signUp.confirmPass)) {
            view.message("7");
            return false;
        }
        return true;
    }

    @Override
    public void doSignUp(SignUpModelToValidation modelToValidation) {
        requestInterface.callApi(AppController.getInstance().service.signUp(ApiRequestParam.getInstance().signUp(modelToValidation)), ApiRequestTypes.SIGNUP);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        view.hideLoader();
        if (Validation.isValidObject(response) && response.isSuccessful()) {
            switch (reqType) {
                case ApiRequestTypes.SIGNUP: {
                    SignupRes res = (SignupRes) response.body();
                    view.signupRes(res);
                }
                break;
            }
        } else {

        }
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        view.hideLoader();
        view.message(responseError.getMessage());
    }
}
