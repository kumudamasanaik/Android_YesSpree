package com.yesspree.app.screens.login

import com.yesspree.app.AppController
import com.yesspree.app.apipresenter.ApiResponsePresenter
import com.yesspree.app.interfaces.IResponseInterface
import com.yesspree.app.modelapi.UserData
import com.yesspree.app.network.ApiRequestParam
import com.yesspree.app.network.ApiRequestTypes
import retrofit2.Call
import retrofit2.Response

class LoginPresenter constructor(var view: ILoginView) : ILoginPresenter, IResponseInterface {
    private var iRequest: ApiResponsePresenter

    init {
        this.view = view
        this.iRequest = ApiResponsePresenter(this)
    }

    override fun doLogin(userNameMob: String, pass: String) {
        iRequest.callApi(AppController.getInstance().service.doLogin(ApiRequestParam.getInstance().login(userNameMob, pass)), ApiRequestTypes.LOGIN)
    }

    override fun forgotPass(mobOrEmil: String, isemail: Boolean) {
        iRequest.callApi(AppController.getInstance().service.forgotPasss(ApiRequestParam.getInstance().forgotPass(mobOrEmil, isemail)), ApiRequestTypes.FORGOT_PASS)
    }

    override fun onResponseSuccess(call: Call<*>?, response: Response<*>?, reqType: String?) {
        view.hideLoader()
        if (response!!.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.LOGIN ->
                    view.showLoginRes(response.body() as UserData)
                ApiRequestTypes.FORGOT_PASS ->
                    view.showForgotpassRes(response.body() as UserData)
            }
        }
    }

    override fun onResponseFailure(call: Call<*>?, responseError: Throwable?, reqType: String?) {
        view.hideLoader()
        view.showMsg(responseError?.message)
    }
}