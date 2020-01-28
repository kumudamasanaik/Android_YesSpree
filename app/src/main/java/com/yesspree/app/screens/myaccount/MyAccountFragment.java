package com.yesspree.app.screens.myaccount;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.MyAccount;
import com.yesspree.app.modelapi.MyAccountRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.addaddress.AddAddressActivity;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.resetpass.ResetPassActivity;
import com.yesspree.app.screens.signup.SignUpModelToValidation;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.TimeUtils;
import com.yesspree.app.utility.Validation;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class MyAccountFragment extends Fragment implements IMyAccountView {


    @BindView(R.id.view_profile)
    FrameLayout viewProfile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_edit_address)
    AppCompatImageView ivEditAddress;
    @BindView(R.id.view_address)
    LinearLayout viewAddress;
    @BindView(R.id.view_seperator)
    View viewSeperator;
    @BindView(R.id.tv_passwrd)
    TextView tvPasswrd;
    @BindView(R.id.view_seperator2)
    View viewSeperator2;
    @BindView(R.id.view_seperatorparent)
    View viewSeperatorparent;
    @BindView(R.id.view_seperator_name)
    View viewSeperatorName;
    @BindView(R.id.uesrename_view)
    LinearLayout uesrenameView;
    @BindView(R.id.view_seperator_lastname)
    View viewSeperatorLastname;
    @BindView(R.id.lastname_view)
    LinearLayout lastnameView;
    @BindView(R.id.view_seperator_emailid)
    View viewSeperatorEmailid;
    @BindView(R.id.emailid_view)
    LinearLayout emailidView;
    @BindView(R.id.view_seperator_dob)
    View viewSeperatorDob;
    @BindView(R.id.date_of_brth_view)
    LinearLayout dateOfBrthView;
    @BindView(R.id.view_seperator_numbr)
    View viewSeperatorNumbr;
    @BindView(R.id.mobile_numbr_view)
    LinearLayout mobileNumbrView;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @BindView(R.id.multistateview)
    MultiStateView multistateview;
    @BindView(R.id.tv_firstName)
    EditText tvFirstName;
    @BindView(R.id.tv_lastName)
    EditText tvLastName;
    @BindView(R.id.tv_emailId)
    EditText tvEmailId;
    @BindView(R.id.tv_DOB)
    EditText tvDOB;
    @BindView(R.id.tv_mblNmbr)
    EditText tvMblNmbr;

    @BindView(R.id.iv_calenderview)
    AppCompatImageView calenderView;


    @BindView(R.id.iv_offer1)
    AppCompatImageView myProfileImage;
    static Bitmap bitmap;


    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private String TAG = "MyAccountFragment";
    private View view;
    Unbinder unbinder;
    MyAccountPresenter myAccountPresenter;
    MyAccount myAccount;
    MyAccountRespModel myAccountRespModel;
    AddressData addressData;
    private int mYear, mMonth, mDay;
    static String resultImageString;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            MyLogUtils.e(TAG, "onAttach");
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void init() {
        myAccountPresenter = new MyAccountPresenter(this);
        tvDOB.setEnabled(false);
        getMyAccountDetails();
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(mContext, msg, 1);

    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), true);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        getMyAccountDetails();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        getMyAccountDetails();
    }

    @Override
    public void getMyAccountDetails() {
        if (CommonUtils.checkLoginStatus(mContext, getString(R.string.error_user_not_login))) {
            if (NetworkStatus.getInstance().isOnline2(mContext)) {
                showViewState(MultiStateView.VIEW_STATE_LOADING);
                myAccountPresenter.callMyAcountDetails(CommonUtils.getAuthorizationKey(mContext));
            } else {
                showMsg(getString(R.string.error_no_internet));
                showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }

    }

    @Override
    public void setMyAccountDetails(MyAccountRespModel data) {
        if (multistateview != null) {
            if (Validation.isValidStatus(data)) {
                showViewState(MultiStateView.VIEW_STATE_CONTENT);
                if (Validation.isValidObject(data)) {
                    myAccountRespModel = data;
                    if (Validation.isValidObject(myAccountRespModel.getAddressData()))
                        addressData = myAccountRespModel.getAddressData();
                    setMyAccoutData();
                } else
                    showViewState(MultiStateView.VIEW_STATE_EMPTY);
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void updateAccountDetailsResp(MyAccountRespModel data) {

        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                CommonUtils.showToastMsg(mContext, "SUCCESS", 1);
                Intent intent = new Intent(mContext, DashBoardActivity.class);
                intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH);
                startActivity(intent);
            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }


    @Override
    public void showViewState(int state) {
        if (multistateview != null)
            multistateview.setViewState(state);
    }


    private void setMyAccoutData() {
        updateAddressUI();
        if (Validation.isValidList(myAccountRespModel.getMyAccount())) {
            myAccount = myAccountRespModel.getMyAccount().get(0);

            if (Validation.isValidString(myAccount.getFirstName()))
                tvFirstName.setText(myAccount.getFirstName());

            if (Validation.isValidString(myAccount.getLastName()))
                tvLastName.setText(myAccount.getLastName());

            if (Validation.isValidString(myAccount.getEmail()))
                tvEmailId.setText(myAccount.getEmail());

            if (Validation.isValidString(myAccount.getMobile()))
                tvMblNmbr.setText(myAccount.getMobile());

            if (Validation.isValidString(myAccount.getPic()))
                ImageUtils.setImageWitDefPic(mContext, myProfileImage, myAccount.getPic());
            if (Validation.isValidString(myAccount.getDateOfBirth()))
                tvDOB.setText(TimeUtils.getdate(myAccount.getDateOfBirth()));

        }

    }

    private void updateAddressUI() {
        if (Validation.isValidString(addressData.getCity()) && Validation.isValidString(addressData.getState()) && Validation.isValidObject(tvAddress))
            tvAddress.setText(addressData.getCity().concat(",").concat(addressData.getState()));

    }

    @OnClick(R.id.iv_edit_address)
    public void editAddress(View v) {
        Intent move = new Intent(mContext, AddAddressActivity.class);
        move.putExtra(Constants.SOURCE, Constants.SOURCE_EDIT_ADDRESS_MY_ACCOUNT);
        move.putExtra(Constants.EXTRAS_ADDRESS_DATA, addressData);
        startActivityForResult(move, Constants.MY_ACCOUNT_CODE);


    }


    @OnClick(R.id.tv_passwrd)
    void changePasswrd(View view) {
        Intent move = new Intent(mContext, ResetPassActivity.class);
        move.putExtra(Constants.SOURCE, Constants.SOURCE_EDIT_PSSWRD_MY_ACCOUNT);
        startActivityForResult(move, Constants.ACCOUNT_EDIT_PSSWRDCODE);
    }

    @OnClick(R.id.iv_calenderview)
    void pickTheDate(View view) {
        showDatePickerDialog();
    }

    @OnClick(R.id.layout_editview)
    void setupCropImage(View view) {
        setupCropTheImageView();
    }


    @OnClick(R.id.tv_save)
    public void saveAddress(View v) {
        if (CommonUtils.checkLoginStatus(mContext, getString(R.string.error_user_not_login))) {
            if (NetworkStatus.getInstance().isOnline2(mContext)) {
                if (!isValidData())
                    return;

                SignUpModelToValidation signUpModelToValidation = new SignUpModelToValidation();
                signUpModelToValidation.first_name = tvFirstName.getText().toString();
                signUpModelToValidation.last_name = tvLastName.getText().toString();
                signUpModelToValidation.mobile = tvMblNmbr.getText().toString();
                signUpModelToValidation.email = tvEmailId.getText().toString();

                if (Validation.isValidString(tvDOB.getText().toString())) {
                    String date = TimeUtils.getdateapi(tvDOB.getText().toString());

                    MyLogUtils.d(TAG, "SERVER DATE : " + date);
                    signUpModelToValidation.dateOfBrth = date;

                }


                if (Validation.isValidString(resultImageString))
                    signUpModelToValidation.profilPic = resultImageString;
                showViewState(MultiStateView.VIEW_STATE_LOADING);
                myAccountPresenter.saveUpdatedAccountDetails(signUpModelToValidation);
            } else {
                showMsg(getString(R.string.error_no_internet));
                showViewState(MultiStateView.VIEW_STATE_ERROR);
            }

        }


    }


    private void showDatePickerDialog() {

        // Get Current Date
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.MyDatePickerDialogTheme,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDay = dayOfMonth;
                    setupDateOfBrth();

                }, mYear, mMonth, mDay);
        datePickerDialog.show();

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());


    }

    private void setupDateOfBrth() {
        tvDOB.setText(mDay + "-" + mMonth + "-" + mYear);
    }


    private void setupCropTheImageView() {
        CropImage.activity()
                .start(mContext, this);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                myProfileImage.setImageURI(result.getUri());
                bitmap = ((BitmapDrawable) myProfileImage.getDrawable()).getBitmap();
                new getBase64Image(bitmap).execute();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                MyLogUtils.d(TAG, error.toString());
            }
        }

        if (requestCode == Constants.MY_ACCOUNT_CODE) {
            if (data != null) {
                // addressData = data.getParcelableExtra(Constants.EXTRAS_ADDRESS_DATA);
                getMyAccountDetails();
            }
        }
    }

    public boolean isValidData() {


        if (!Validation.isValidString(tvFirstName.getText().toString())) {
            tvFirstName.setError(getString(R.string.err_firstname));
            return false;
        }
        if (!Validation.isValidString(tvLastName.getText().toString())) {
            tvLastName.setError(getString(R.string.err_lastname));
            return false;
        }

        if (!Validation.isValidEmail(tvEmailId.getText().toString())) {
            tvEmailId.setError(getString(R.string.err_emailId));
            return false;
        }
        if (!Validation.isValidString(tvDOB.getText().toString())) {
            tvDOB.setError(getString(R.string.valid_dob));
            return false;
        }

        if (!Validation.isValidMobile(tvMblNmbr.getText().toString())) {
            tvMblNmbr.setError(getString(R.string.err_mobile_no));
            return false;
        }

        return true;
    }


    public static class getBase64Image extends AsyncTask<Void, Void, String> {
        Bitmap bitmap;

        public getBase64Image(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return CommonUtils.convertToBase64(bitmap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            resultImageString = s;
            MyLogUtils.d("My Account", "IMAGE URL :" + resultImageString);

        }
    }


}
