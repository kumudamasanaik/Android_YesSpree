package com.yesspree.app.screens.resetpass

import com.yesspree.app.modelapi.UserData
import com.yesspree.app.screens.common.IBaseView

interface IResetPassView : IBaseView {
    fun resetPassRes(res: UserData)
}