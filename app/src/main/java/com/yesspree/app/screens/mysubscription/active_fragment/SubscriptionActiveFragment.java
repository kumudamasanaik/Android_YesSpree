package com.yesspree.app.screens.mysubscription.active_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IPaymentSelection;
import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.screens.mysubscription.SubscriptionAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SubscriptionActiveFragment extends Fragment implements IActiveView, IAdapterClickListener {

    @BindView(R.id.tv_active_subscription)
    TextView tvActiveSubscription;
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.rv_subscription)
    RecyclerView recyclerView;

    /*variables*/
    String subScrType;
    Unbinder unbinder;
    private Context context;
    private FragmentActivity activity;
    public ActivePresenter presenter;
    ArrayList<MyOrdersRespModel.MyOrders> mySubscriptionsArrayList;
    SubscriptionAdapter adapter;
    private LinearLayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    int cancelPos = -1;

    public SubscriptionActiveFragment() {
        // Required empty public constructor
    }

    public static SubscriptionActiveFragment newInstance(@Nonnull String subScrType) {
        SubscriptionActiveFragment fragment = new SubscriptionActiveFragment();
        Bundle args = new Bundle();
        args.putString(Constants.MY_SUBSCRIPTION, subScrType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subScrType = getArguments().getString(Constants.MY_SUBSCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        showViewState(MultiStateView.VIEW_STATE_EMPTY);
        presenter = new ActivePresenter(this);
        showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callMySubscrApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callMySubscrApi();
    }

    @Override
    public void callMySubscrApi() {
        if (Validation.isValidString(subScrType)) {
            if (Validation.isValidString(CommonUtils.getAuthorizationKey(context))) {
                showViewState(MultiStateView.VIEW_STATE_LOADING);
                presenter.getSubscritionList(CommonUtils.getAuthorizationKey(context), subScrType);
            }
        } else
            showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void setMySubscrRes(MyOrdersRespModel res) {
        if (Validation.isValidStatus(res)) {
            if (Validation.isValidList(res.getOrderDataArrayList())) {
                showViewState(MultiStateView.VIEW_STATE_CONTENT);
                mySubscriptionsArrayList = res.getOrderDataArrayList();
                setUpData();
            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    private void setUpData() {
        layoutManager = new LinearLayoutManager(context);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_2dp, null));
        adapter = new SubscriptionAdapter(context, this, subScrType);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.addList(mySubscriptionsArrayList);
        setSizeOflist();
    }

    private void setSizeOflist() {
        if (Validation.isValidList(mySubscriptionsArrayList)) {
            tvActiveSubscription.setText(subScrType.equals(Constants.ACTIVE) ?
                    getString(R.string.total_active_subscriptions, mySubscriptionsArrayList.size()) :
                    getString(R.string.total_cancel_subscriptions, mySubscriptionsArrayList.size()));
        }
    }

    @Override
    public void setCancelSubscrRes(CancelOrder res) {
        if (Validation.isValidStatus(res)) {
            if (cancelPos == -1)
                return;
            mySubscriptionsArrayList.remove(cancelPos);
            adapter.notifyItemRemoved(cancelPos);
            setSizeOflist();
        } else if (Validation.isValidStrMsg(res))
            showMsg(res.getMessage());
        else
            showMsg(null);
        cancelPos = -1;
    }


    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong), 0);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), false);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        cancelPos = -1;
        if (data == null)
            return;
        if (data instanceof MyOrdersRespModel.MyOrders) {
            MyOrdersRespModel.MyOrders myOrder = (MyOrdersRespModel.MyOrders) data;
            if (subScrType.equals(Constants.ACTIVE)) {
                if (Validation.isValidObject(myOrder.getOrderData()) && Validation.isValidString(myOrder.getOrderData().getOrderNumber()))
                    showConfDialog(context, myOrder.getOrderData().getOrderNumber(), pos);
            }
        }
    }

    public void showConfDialog(Context context, String orderNum, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dailogview = inflater.inflate(R.layout.partail_popup_confirmation_dialog, null);
        TextView dialog_header = dailogview.findViewById(R.id.dialog_header);
        TextView dialog_body = dailogview.findViewById(R.id.dialog_body);

        dialog_header.setText(getString(R.string.cancel));
        dialog_body.setText(getString(R.string.are_you_sure_want_cancel_subscr));
        TextView ok = dailogview.findViewById(R.id.btn_ok);
        TextView cancel = dailogview.findViewById(R.id.btn_cancel);

        ok.setText(getString(R.string.yes));
        cancel.setText(getString(R.string.No));

        builder.setView(dailogview);

        final AlertDialog dialog = builder.create();
        ok.setOnClickListener(v -> {
            showLoader(null);
            cancelPos = pos;
            presenter.cancelSubscrition(orderNum);
            dialog.dismiss();
        });


        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
