package com.yesspree.app.screens.resetpass

import com.yesspree.app.AppController
import com.yesspree.app.apipresenter.ApiResponsePresenter
import com.yesspree.app.interfaces.IResponseInterface
import com.yesspree.app.modelapi.UserData
import com.yesspree.app.network.ApiConstants
import com.yesspree.app.network.ApiRequestParam
import com.yesspree.app.network.ApiRequestTypes
import retrofit2.Call
import retrofit2.Response

class ResetPassPresenter constructor(var view: IResetPassView) : IResponseInterface, IResetPassPresenter {

    private var iRequest: ApiResponsePresenter

    init {
        this.view = view
        this.iRequest = ApiResponsePresenter(this)
    }

    override fun resetPass(pass: String, id: String) {
        iRequest.callApi(AppController.getInstance().service.resetPass(ApiRequestParam.getInstance().resetPass(pass, id)), ApiRequestTypes.RESET_PASS)
    }

    override fun onResponseSuccess(call: Call<*>?, response: Response<*>?, reqType: String?) {
        view.hideLoader()
        if (response!!.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.RESET_PASS -> view.resetPassRes(response.body() as UserData)
            }
        }
    }

    override fun onResponseFailure(call: Call<*>?, responseError: Throwable?, reqType: String?) {
        view.hideLoader()
        view.showMsg(responseError?.message)
    }

}