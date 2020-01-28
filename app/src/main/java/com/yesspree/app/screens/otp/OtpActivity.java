package com.yesspree.app.screens.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SignupRes;
import com.yesspree.app.modelapi.UserData;
import com.yesspree.app.screens.login.LoginActivity;
import com.yesspree.app.screens.resetpass.ResetPassActivity;
import com.yesspree.app.screens.signup.SignupActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.RealTimePermission;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpActivity extends AppCompatActivity implements IOtpView {

    @BindView(R.id.et_otp)
    AppCompatEditText etOtp;
    @BindView(R.id.tv_continue)
    AppCompatTextView tvContinue;
    @BindView(R.id.tv_resend_otp)
    AppCompatTextView tvResendOtp;
    @BindView(R.id.layout_otp_parent)
    RelativeLayout layoutOtpParent;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;

    /*veriable*/
    Context context;
    OtpPresenter presenter;
    String source, mobile, otp;
    SignupRes.SignupResult userModel;
    private OtpReceiver otpReceiver;
    private IntentFilter intentFilter;
    private String TAG = "OtpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        context = OtpActivity.this;
        if (getIntent() != null && getIntent().getExtras() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            mobile = getIntent().getStringExtra(Constants.MOBILE);
            userModel = getIntent().getParcelableExtra(Constants.MODEL);
            init();
            return;
        }
        finish();

    }

    @Override
    public void init() {
        if (!RealTimePermission.checkPermissionSMS(context)) {
            RealTimePermission.requestSMSPermission(context, RealTimePermission.SMS_PERMISSION_REQUEST_CODE);
        }
        if (Validation.isValidString(source)) {
            if (source.equalsIgnoreCase(Constants.SOURCE_SIGN))
                tvTitleText.setText(getString(R.string.forgot_Password));
        }
        otpReceiver = new OtpReceiver();
        intentFilter = new IntentFilter(Constants.OTP_BROADCAST_INTENT);
        presenter = new OtpPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RealTimePermission.checkPermissionSMS(context))
            registerReceiver(otpReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (otpReceiver != null)
            unregisterReceiver(otpReceiver);
    }


    @OnClick(R.id.tv_continue)
    void verifyOtp() {
        callVerifyOtpApi();
    }

    private void callVerifyOtpApi() {
        otp = etOtp.getText().toString().trim();
        if (Validation.isValidString(otp)) {
            showLoader("");
           // if (Validation.isValidString(CommonUtils.getAuthorizationKey(context)))////Authorization key is not modrantary
                presenter.verifyOtp(mobile, otp);

        } else
            etOtp.setError(getString(R.string.err_otp_empty));
    }

    @OnClick(R.id.tv_resend_otp)
    void resendOtp() {

        if (Validation.isValidString(CommonUtils.getAuthorizationKey(context))) {
            showLoader("");
            presenter.resenOtp(CommonUtils.getAuthorizationKey(context));
        }
    }

    @Override
    public void setOtpVerifRes(ResponseModel res) {
        if (Validation.isValidStatus(res)) {
            UserData object = (UserData) res;
            if (Validation.isValidList(object.getCustomerList())) {
                CommonUtils.setCustomerData(context, object.getCustomerList().get(0));
                onSuccess();
                setNavigation();
                /*if (Validation.isValidString(object.getCustomerList().get(0).getEmail())) {
                } else {
                    openRegisterActivity();
                }*/
            } else
                setNavigation();
        } else
            CommonUtils.showToastMsg(context, getString(R.string.error_something_wrong), 1);
    }

    @Override
    public void setOtpResendRes(ResponseModel res) {
        if (Validation.isValidStatus(res)) {
            CommonUtils.showToastMsg(context, (Validation.isValidObject(res) && Validation.isValidString(res.getMessage())) ? res.getMessage() : getString(R.string.error_something_wrong), 1);
        } else
            CommonUtils.showToastMsg(context, (Validation.isValidObject(res) && Validation.isValidString(res.getMessage())) ? res.getMessage() : getString(R.string.error_something_wrong), 1);
    }

    private void setNavigation() {
        switch (source) {
            case Constants.SOURCE_SIGHUP: {
                startLoginScreen();
            }
            break;
            case Constants.SOURCE_SIGN: {
                startRestPassScreen();
            }
            break;
        }
    }

    private void startRestPassScreen() {
        Intent intent = new Intent(context, ResetPassActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_OTP);
        startActivity(intent);
    }

    private void startLoginScreen() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_OTP);
        startActivity(intent);
    }

    private void onSuccess() {
        CommonUtils.showToastMsg(context, getString(R.string.otp_success), 1);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(context, SignupActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_OTP);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        if (Validation.isValidString(msg)) {
            if (msg.equalsIgnoreCase("empty")) {
                CommonUtils.showToastMsg(context, getString(R.string.no_data_found), 1);
            } else
                CommonUtils.showToastMsg(context, getString(R.string.no_data_found), 1);
        }
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, getString(R.string.please_wait), true);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }


    class OtpReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Validation.isValidOtp(intent.getStringExtra(Constants.OTP))) {
                etOtp.setText(intent.getStringExtra(Constants.OTP));
                MyLogUtils.e(TAG, "BroadcastReceiver onReceive");
                callVerifyOtpApi();
            }
        }
    }
}
