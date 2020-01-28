package com.yesspree.app.screens.resetpass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesspree.app.R
import com.yesspree.app.constatnts.Constants
import com.yesspree.app.modelapi.UserData
import com.yesspree.app.screens.myaccount.MyAccountFragment
import com.yesspree.app.utility.CommonUtils
import com.yesspree.app.utility.Validation
import kotlinx.android.synthetic.main.activity_rest_pass.*

class ResetPassActivity : AppCompatActivity(), IResetPassView {


    //veriable
    lateinit var context: Context
    lateinit var presenter: IResetPassPresenter
    lateinit var pass: String
    lateinit var confPass: String
    lateinit var source: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_pass)
        context = this

        if(Validation.isValidString(intent.getStringExtra(Constants.SOURCE_EDIT_PSSWRD_MY_ACCOUNT)))
            source=intent.getStringExtra(Constants.SOURCE_EDIT_PSSWRD_MY_ACCOUNT)

        init()
        tv_done.setOnClickListener {
            resetPass()
        }
    }

    private fun resetPass() {
        pass = et_new_pass.text.toString()
        confPass = et_confirm_pass.text.toString()
        if (Validation.isValidString(pass)) {
            if (Validation.isValidString(confPass)) {
                if (pass.contentEquals(confPass)) {
                    if (Validation.isValidString(CommonUtils.getCustomerId(context))) {
                        showLoader(null)
                        presenter.resetPass(pass, CommonUtils.getCustomerId(context))
                    } else
                        showMsg(getString(R.string.err_user_not_found))
                } else
                    showMsg(getString(R.string.err_conf_pass_match))
            } else
                showMsg(getString(R.string.err_conf_pass))
        } else
            showMsg(getString(R.string.err_password))
    }


    override fun init() {
        presenter = ResetPassPresenter(this)
    }

    override fun resetPassRes(res: UserData) {
        if (Validation.isValidStatus(res)) {
            showMsg(res.message)
            if(Validation.isValidString(source) && source.contentEquals(Constants.SOURCE_EDIT_PSSWRD_MY_ACCOUNT)) {
                startMyAccountScreen()
            }else
                startLoginScreen()
        } else
            showMsg(res.message ?: getString(R.string.error_something_wrong))
    }

    private fun startLoginScreen() {
        val intent = Intent(context, MyAccountFragment::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_RESET_PASS)
        startActivity(intent)
        finish()
    }

    override fun showMsg(msg: String?) {
        CommonUtils.showToastMsg(context, msg, 1)
    }

    override fun showLoader(msg: String?) {
        CommonUtils.showLoading(context, msg ?: getString(R.string.please_wait), false)
    }

    override fun hideLoader() {
        CommonUtils.hideLoading()
    }


    private fun startMyAccountScreen() {
        setResult(Constants.ACCOUNT_EDIT_PSSWRDCODE, Intent().putExtra(Constants.SUCCESS, Constants.SUCCESS))
        finish()
    }
}
