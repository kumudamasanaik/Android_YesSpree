package com.yesspree.app.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import com.yesspree.app.R
import com.yesspree.app.constatnts.Constants
import com.yesspree.app.modelapi.ResponseModel
import com.yesspree.app.modelapi.UserData
import com.yesspree.app.screens.basescreen.DashBoardActivity
import com.yesspree.app.screens.landing.LandingActivity
import com.yesspree.app.screens.otp.OtpActivity
import com.yesspree.app.screens.signup.SignupActivity
import com.yesspree.app.utility.CommonUtils
import com.yesspree.app.utility.Validation
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILoginView {


    //variable
    lateinit var context: Context
    lateinit var presenter: LoginPresenter
    lateinit var mobileOrEmail: String
    lateinit var password: String
    lateinit var mblNumbr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        init()

        tv_sign.setOnClickListener {
            doSignIn()
        }

        tv_dont_have_acc.setOnClickListener {
            startSignUpAct()
        }

        tv_forgo_pass.setOnClickListener {
            doForgotPass()
        }


        tv_gmail_sigh_up.setOnClickListener {
            navigateToLandingScreen(Constants.SOCIAL_TYPE_GOOGLE)
        }

        tv_fb_sigh_up.setOnClickListener {
            navigateToLandingScreen(Constants.SOCIAL_TYPE_FACEBOOK)
        }

    }

    private fun doForgotPass() {
        mobileOrEmail = et_email_or_mob.text.toString()
        if (mobileOrEmail.length > 0) {
            presenter.forgotPass(mobileOrEmail, true)
            showLoader(null)
        } else
            showMsg(getString(R.string.err_email_id_mobile))
    }

    private fun startSignUpAct() {
        var intent: Intent = Intent(context, SignupActivity::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SIGN)
        startActivity(intent)
        finish()
    }

    private fun startOtpScreen() {
        var intent = Intent(context, OtpActivity::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SIGN)
        intent.putExtra(Constants.MOBILE, mobileOrEmail)

        startActivity(intent)
        finish()
    }


    private fun doSignIn() {
        mobileOrEmail = et_email_or_mob.text.toString()
        password = et_pass.text.toString()
        if (mobileOrEmail.length > 0) {
            if (password.length > 0) {
                showLoader(null)
                presenter.doLogin(mobileOrEmail, password)
            } else
                showMsg(getString(R.string.err_password))
        } else
            showMsg(getString(R.string.err_email_id_mobile))

    }


    override fun init() {
        tv_gmail_sigh_up.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_google_plus), null, null, null);
        tv_fb_sigh_up.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_facebook), null, null, null);
        presenter = LoginPresenter(this)
    }


    override fun showLoginRes(res: UserData) {
        if (Validation.isValidStatus(res)) {
            showMsg(res.message)
            var user: UserData = res
            if (Validation.isValidList(user.customerList)) {
                CommonUtils.setCustomerData(context, user.getCustomerList().get(0))
                CommonUtils.setIsLoggedIn(context, true)
                navigateToDahboard()
            } else
                showMsg(res.message ?: getString(R.string.error_something_wrong))
        }
        else{
            showMsg(res.message ?: getString(R.string.error_something_wrong))

        }
    }

    private fun navigateToDahboard() {
        var intent = Intent(context, DashBoardActivity::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SIGN)
        startActivity(intent)
        finish()
    }

    private fun navigateToLandingScreen(socialType: String) {
        var intent = Intent(context, LandingActivity::class.java)
        intent.apply {
            putExtra(Constants.SOURCE, Constants.SOURCE_SIGN)
            putExtra(Constants.SOCIALTYPE, socialType)
        }
        startActivity(intent)
        finish()
    }

    override fun showForgotpassRes(res: ResponseModel) {
        if (Validation.isValidStatus(res)) {
            startOtpScreen()
        } else
            showMsg(res.message ?: getString(R.string.error_something_wrong))
    }

    override fun showMsg(msg: String?) {
        CommonUtils.showToastMsg(context, msg ?: "", 1)
    }

    override fun showLoader(msg: String?) {
        CommonUtils.showLoading(context, msg ?: getString(R.string.please_wait), false)
    }

    override fun hideLoader() {
        CommonUtils.hideLoading()
    }
}
