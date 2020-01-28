package com.yesspree.app.screens.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.SignupRes;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.login.LoginActivity;
import com.yesspree.app.screens.otp.OtpActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.RealTimePermission;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity implements ISignupView {

    @BindView(R.id.iv_log)
    AppCompatImageView ivLog;
    @BindView(R.id.layout_person_prefix)
    LinearLayout layoutPersonPrefix;
    @BindView(R.id.et_first_name)
    AppCompatEditText etFirstName;
    @BindView(R.id.et_last_name)
    AppCompatEditText etLastName;
    @BindView(R.id.et_email_id)
    AppCompatEditText etEmailId;
    @BindView(R.id.et_mobile_num)
    AppCompatEditText etMobileNum;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.et_conf_pass)
    AppCompatEditText etConfPass;
    @BindView(R.id.et_referal_code)
    AppCompatEditText etReferalCode;
    @BindView(R.id.tv_sign_up)
    AppCompatTextView tvSignUp;
    @BindView(R.id.layout_sign_up_detials)
    LinearLayout layoutSignUpDetials;
    @BindView(R.id.tv_already_had_acc)
    AppCompatTextView tvAlreadyHadAcc;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    ISignupPresenter presenter;


    //veriables
    Context context;
    private String TAG = "SignupActivity";
    private String personPrefix = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighup);
        ButterKnife.bind(this);
        context = SignupActivity.this;
        init();
    }

    // TODO: 20-04-2018 remove test data
    private void setDummyData() {
        etFirstName.setText("test");
        etLastName.setText("test");
        etEmailId.setText("test@test.com");
        etMobileNum.setText("8123117611");
        etPassword.setText("test");
        etConfPass.setText("test");
    }

    @Override
    public void init() {
        personPrefix = getString(R.string.pref_mr);
        presenter = new SignupPresenter(this);
        setDummyData();
    }

    @Override
    public void signUp() {

    }

    @Override
    public void message(String msg) {
        switch (msg) {
            case "1":
                etFirstName.setError(getString(R.string.err_firstname));
                break;
            case "2":
                etLastName.setError(getString(R.string.err_lastname));
                break;
            case "3":
                etEmailId.setError(getString(R.string.err_emailId));
                break;
            case "4":
                etMobileNum.setError(getString(R.string.err_mobile_no));
                break;
            case "5":
                etPassword.setError(getString(R.string.err_password));
                break;
            case "6":
                etConfPass.setError(getString(R.string.err_conf_pass));
                break;
            case "7":
                etConfPass.setError(getString(R.string.err_conf_pass_match));
                break;
            case "8":
                etEmailId.setError(getString(R.string.err_emailId_invalid));
                break;
            case "9":
                CommonUtils.showToastMsg(context, getString(R.string.err_sel_per_pref), 1);
            default: {
                if (Validation.isValidString(msg))
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.tv_sign_up)
    public void signUpclick(View view) {
        SignUpModelToValidation signUpModel = new SignUpModelToValidation();
        signUpModel.first_name = etFirstName.getText().toString().trim();
        signUpModel.last_name = etLastName.getText().toString().trim();
        signUpModel.email = etEmailId.getText().toString().trim();
        signUpModel.mobile = etMobileNum.getText().toString().trim();
        signUpModel.password = etPassword.getText().toString().trim();
        signUpModel.confirmPass = etConfPass.getText().toString().trim();
        signUpModel.referred_code = etReferalCode.getText().toString().trim();
        signUpModel.person_prefix = personPrefix;
        if (presenter.validate(signUpModel)) {
            if (NetworkStatus.getInstance().isOnline2(context)) {
                showLoader();
                presenter.doSignUp(signUpModel);
            } else
                CommonUtils.showToastMsg(context, getString(R.string.error_no_internet), 1);
        }
    }

    @OnClick(R.id.tv_already_had_acc)
    public void clickAlreadyAcc() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SIGHUP);
        startActivity(intent);
        finish();
    }

    @Override
    public void signupRes(SignupRes res) {
        if (Validation.isValidStatus(res)) {
            if (Validation.isValidString(res.getMessage())) {
                if (res.getMessage().equalsIgnoreCase(getString(R.string.existing_user))) {
                    CommonUtils.showToastMsg(context, getString(R.string.user_is_alreay_existed), 1);
                } else if (Validation.isValidList(res.getSignupResult()) && Validation.isValidString(res.getSignupResult().get(0).getMobile())) {
                    CommonUtils.showToastMsg(context, getString(R.string.sign_up_success), 1);
                    startOtpScreen(res);
                }
            }
        } else if (Validation.isValidString(res.getMessage())) {
            CommonUtils.showToastMsg(context, res.getMessage(), 1);
        } else CommonUtils.showToastMsg(context, getString(R.string.error_something_wrong), 1);
    }

    private void startOtpScreen(SignupRes res) {
        Intent intent = new Intent(context, OtpActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SIGHUP);
        intent.putExtra(Constants.MOBILE, res.getSignupResult().get(0).getMobile());
        intent.putExtra(Constants.MODEL, res.getSignupResult().get(0));
        if (Validation.isValidString(res.getSignupResult().get(0).getOtp()))
            intent.putExtra(Constants.OTP, res.getSignupResult().get(0).getOtp());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!RealTimePermission.checkPermissionSMS(context)) {
            RealTimePermission.requestSMSPermission(context, RealTimePermission.SMS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void showLoader() {
        CommonUtils.showLoading(context, getString(R.string.please_wait), true);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((AppCompatRadioButton) view).isChecked();
        personPrefix = ((AppCompatRadioButton) view).getText().toString();
        MyLogUtils.d(TAG, personPrefix);
        switch (view.getId()) {
            case R.id.rdbt_mr:
                if (checked) {
                }
                break;
            case R.id.rdbt_mrs:
                if (checked) {
                }
                break;
            case R.id.rdbt_miss:
                if (checked) {
                }
                break;
            case R.id.rdbt_ms:
                if (checked) {
                }
                break;
            default:
                break;
        }
    }
}
