package com.yesspree.app.screens.faq;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.FAQRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.faq.Adapter.FAQHeadersAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FAQActivity extends SubBaseActivity implements IFAQView, IAdapterClickListener {
    private String TAG = "FAQActivity";

    private Context mContext;
    private String source;
    @BindView(R.id.rv_faq_header_list)
    RecyclerView rv_FaqHeaders;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    List<Category> list;

    FAQPresenter faqPresenter;
    FAQRespModel faqRespModel;

    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;
    @BindView(R.id.empty_button)
    TextView btnEmptyCart;

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.faq));
        tvEpmtyCart.setText(getString(R.string.err_empty_faq_list));
        btnEmptyCart.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_faq, fragmentLayout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();
    }

    @Override
    public void init() {
        mContext = FAQActivity.this;
        faqPresenter = new FAQPresenter(this);
        callFAQApi();

    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(this, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);

    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(this, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }


    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        if (Validation.isValidList(list) && list.size() > pos)
            rv_FaqHeaders.smoothScrollToPosition(pos);

    }

    @Override
    public void callFAQApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        if (NetworkStatus.getInstance().isOnline2(this)) {
            faqPresenter.callFAQApiCall();

        } else {
            showMsg(getString(R.string.error_no_internet));
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callFAQApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callFAQApi();
    }


    @Override
    public void FaqApiResp(FAQRespModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data.getMainQuestion())) {
                faqRespModel = data;
                setupQuestionRecyclerData();
            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    private void setupQuestionRecyclerData() {
        if (Validation.isValidObject(faqRespModel.getMainQuestion())) {
            FAQHeadersAdapter faqHeadersAdapter = new FAQHeadersAdapter(mContext, faqRespModel.getMainQuestion(), this);
            rv_FaqHeaders.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            rv_FaqHeaders.addItemDecoration(new InsetDecoration(mContext));
            rv_FaqHeaders.hasFixedSize();
            rv_FaqHeaders.setAdapter(faqHeadersAdapter);
            rv_FaqHeaders.setNestedScrollingEnabled(false);
        } else {

        }

    }
}
