package com.yesspree.app.screens.customercare;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.CustomerCare;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerCaresActivity extends SubBaseActivity {

    private String TAG = "CustomerCaresActivity";
    private String source;
    private Context context;
    private CustomerCare customerCare;

    @BindView(R.id.email)
    TextView tvEmail;
    @BindView(R.id.mobile)
    TextView tvMobile;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.customer_care));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_customer_cares, fragmentLayout);
        ButterKnife.bind(this);
        this.context = this;

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            source = bundle.getString(Constants.SOURCE);
            customerCare = bundle.getParcelable(Constants.EXTRA_CUSTOMER_CARE_DATA);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        updateCustomerCareData();
    }

    private void updateCustomerCareData() {
        if (Validation.isValidObject(customerCare)) {
            if (Validation.isValidString(customerCare.getCustomer_e_mail()))
                tvEmail.setText(customerCare.getCustomer_e_mail());
            if (Validation.isValidString(customerCare.getCustomer_mobile()))
                tvMobile.setText(customerCare.getCustomer_mobile());
        }
    }

    @OnClick(R.id.email)
    public void onClickEmail(View view) {
        if (!TextUtils.isEmpty(tvEmail.getText())) {
            CommonUtils.goToEmailApp(context, tvEmail.getText().toString().trim(), "Yesspree Android App");
        }
    }

    @OnClick(R.id.mobile)
    public void onClickMobile(View view) {
        if (!TextUtils.isEmpty(tvMobile.getText())) {
            CommonUtils.goToDialPad(context, tvMobile.getText().toString().trim());
        }
    }
}
