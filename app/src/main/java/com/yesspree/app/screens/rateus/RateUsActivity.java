package com.yesspree.app.screens.rateus;

import android.content.Context;
import android.os.Bundle;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.MyLogUtils;

import butterknife.ButterKnife;

public class RateUsActivity extends SubBaseActivity implements IRateUsView {

    private String TAG = "RateUsActivity";
    private Context mContext;
    private String source;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.rate_us));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_rate_us, fragmentLayout);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();

    }

    @Override
    public void init() {
        mContext = RateUsActivity.this;
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {

    }

    @Override
    public void hideLoader() {

    }
}
