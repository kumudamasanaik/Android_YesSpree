package com.yesspree.app.screens.login

interface ILoginPresenter {

    fun doLogin(userNameMob: String, pass: String)

    fun forgotPass(mobOrEmil: String, isemail: Boolean)
}