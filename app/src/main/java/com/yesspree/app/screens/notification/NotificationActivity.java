package com.yesspree.app.screens.notification;

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
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.modelapi.NotificationRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends SubBaseActivity implements INotificationView {

    private String TAG = "NotificationActivity";
    private Context mContext;
    private String source;
    private HomeRecyclerAdapter mNotificationRecyclerAdapter;


    @BindView(R.id.recyclerview_notification)
    RecyclerView recyclerviewNotification;
    NotificationPresenter notificationPresenter;

    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;
    @BindView(R.id.empty_button)
    TextView btnEmptyCart;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    NotificationRespModel notificationRespModel;

    @Override
    public void setSpecificScreenData() {

        title.setText(getString(R.string.notification));
        tvEpmtyCart.setText(getString(R.string.err_empty_notification_list));
        btnEmptyCart.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_notification, fragmentLayout);
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
        mContext = NotificationActivity.this;
        notificationPresenter = new NotificationPresenter(this);
        setupNotificationRecyclerviewData();
        getNotificationListData();

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
    public void getNotificationListData() {
        if (CommonUtils.isLoggedIn(this)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            if (NetworkStatus.getInstance().isOnline2(this)) {
                notificationPresenter.callNotificationApi(CommonUtils.getCustomerId(this));

            } else {
                /// showMsg(getString(R.string.error_no_internet));
                showViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        } else {
            showViewState(MultiStateView.VIEW_STATE_EMPTY);
        }


    }

    @Override
    public void setNotificationResp(NotificationRespModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            notificationRespModel = data;
            if (Validation.isValidList(notificationRespModel.getNotificationArrayList())) {
                upDateNotificationCartCount(notificationRespModel.getNotificationArrayList().size());
                mNotificationRecyclerAdapter.addList(notificationRespModel.getNotificationArrayList());
            } else {
                showViewState(MultiStateView.VIEW_STATE_EMPTY);

            }

        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        getNotificationListData();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        getNotificationListData();
    }

    private void setupNotificationRecyclerviewData() {

        mNotificationRecyclerAdapter = new HomeRecyclerAdapter(mContext, R.layout.notification_recycler_list_item);
        recyclerviewNotification.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerviewNotification.addItemDecoration(new InsetDecoration(mContext));
        recyclerviewNotification.setAdapter(mNotificationRecyclerAdapter);
        recyclerviewNotification.setNestedScrollingEnabled(false);
    }


}
