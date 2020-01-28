package com.yesspree.app.screens.login

import com.yesspree.app.modelapi.ResponseModel
import com.yesspree.app.modelapi.UserData
import com.yesspree.app.screens.common.IBaseView

interface ILoginView : IBaseView {
    fun showLoginRes(res: UserData)
    fun showForgotpassRes(res: ResponseModel)
}