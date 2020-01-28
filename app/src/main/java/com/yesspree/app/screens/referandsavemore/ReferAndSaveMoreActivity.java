package com.yesspree.app.screens.referandsavemore;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.Refferal;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReferAndSaveMoreActivity extends SubBaseActivity implements IReferAndSaveMoreView {

    private String TAG = "ReferAndSaveMoreActivity";
    private Context mContext;
    private String source;


    @BindView(R.id.iv_refferal_icon)
    AppCompatImageView iv_refferal_icon;

    @BindView(R.id.tv_referal_code)
    TextView tv_referal_code;

    @BindView(R.id.tv_referal_link)
    TextView tv_referal_link;

    @BindView(R.id.tv_header_referal)
    TextView tv_header_referal;


    @BindView(R.id.share_facebook)
    ImageView share_facebook;

    @BindView(R.id.share_msg)
    ImageView share_msg;

    @BindView(R.id.share_whatsp)
    ImageView share_whatsp;

    private Refferal refferal;
    ReferAndSaveMorePresenter referAndSaveMorePresenter;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.refer_and_save_more));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_refer_and_save_more, fragmentLayout);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            refferal = getIntent().getParcelableExtra(Constants.EXTRA_REFFERAL_DATA);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();

    }

    @Override
    public void init() {
        referAndSaveMorePresenter = new ReferAndSaveMorePresenter();
        updateReferUI();
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


    @OnClick(R.id.ic_share)
    public void sharViaShareIt() {
        CommonUtils.shareApplication(this,refferal.getShare_msg());

    }


    @OnClick(R.id.share_facebook)
    public void sharViaFacebook() {
        CommonUtils.initShareIntent(this, Constants.FACEBOOK_SHARE, refferal.getShare_msg());

    }

    @OnClick(R.id.tv_referal_link)
    public void toggleReferalView() {

        if (tv_referal_link.getText().toString().equalsIgnoreCase(getString(R.string.referral_link))) {
            tv_header_referal.setText(getString(R.string.your_referral_link));
            tv_referal_link.setText(getString(R.string.referral_code));
            if (Validation.isValidString(refferal.getReferal_link()))
                tv_referal_code.setText(refferal.getReferal_link());
            tv_referal_link.setPaintFlags(tv_referal_link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            tv_header_referal.setText(getString(R.string.your_referral_code));
            tv_referal_link.setText(getString(R.string.referral_link));
            tv_referal_link.setPaintFlags(tv_referal_link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (Validation.isValidString(refferal.getRefer_code()))
                tv_referal_code.setText(refferal.getRefer_code());


        }
    }


    @OnClick(R.id.share_msg)
    public void sharViaMsg() {
        CommonUtils.initShareIntent(this, Constants.MSG_SHARE, refferal.getShare_msg());
    }


    @OnClick(R.id.share_whatsp)
    public void sharViaWhatsup() {
        CommonUtils.initShareIntent(this, Constants.WHATSUP_SHARE, refferal.getShare_msg());

    }


    private void updateReferUI() {

        if (Validation.isValidObject(refferal)) {
            if (Validation.isValidString(refferal.getImage()))
                ImageUtils.setImage(iv_refferal_icon, refferal.getImage());

            if (Validation.isValidString(refferal.getRefer_code()))
                tv_referal_code.setText(refferal.getRefer_code());


        }


    }
}
