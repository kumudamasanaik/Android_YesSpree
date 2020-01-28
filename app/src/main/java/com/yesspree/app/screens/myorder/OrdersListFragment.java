package com.yesspree.app.screens.myorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
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
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.myorderstatus.MyOrderStatusActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdersListFragment extends Fragment implements IAdapterClickListener, IMyOrdersView {
    @BindView(R.id.rv_my_order)
    RecyclerView rvMyOrder;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;


    @BindView(R.id.empty_textView)
    TextView tvEpmty;
    @BindView(R.id.empty_button)
    TextView btnEpmty;

    //variables
    View view;
    OrderListAdapter adapter;
    Context context;
    private FragmentActivity mActivity;
    LinearLayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    MyOrderPresenter myOrderPresenter;
    MyOrdersRespModel myOrdersRespModel;
    ArrayList<MyOrdersRespModel.MyOrders> orderDataArrayList;
    MyOrdersRespModel.MyOrders orderStatus;
    MyOrdersRespModel.MyOrders cancelOrder;
    String orderType;
    private OnFragmentInteractionListener mListener;
    private String TAG = "OrdersListFragment";


    public OrdersListFragment() {
        // Required empty public constructor
    }


    public static OrdersListFragment newInstance(String type) {
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderType = getArguments().getString(Constants.TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void init() {
        myOrderPresenter = new MyOrderPresenter(this);
        showViewState(MultiStateView.VIEW_STATE_EMPTY);
        tvEpmty.setText(getString(R.string.err_empty_my_order_list, orderType));
        btnEpmty.setText(getString(R.string.no_data_found));
        setupOrdersRecyclerView();
        getMyOrdersList();
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        cancelOrder = null;
        if (!Validation.isValidString(opType) || !Validation.isValidObject(data))
            return;
        if (data instanceof MyOrdersRespModel.MyOrders && Validation.isValidObject(((MyOrdersRespModel.MyOrders) data).getOrderData())) {
            if (opType.equals(Constants.ORDER_DETAILS)) {
                orderStatus = (MyOrdersRespModel.MyOrders) data;
                if (orderStatus.getOrderData().getOrderNumber() != null)
                    startOrderStatusScreen(orderStatus.getOrderData().getOrderNumber());
            }
            if (opType.equals(Constants.CANCELLED)) {
                CommonUtils.showConfirmationDialog(mActivity, this, getString(R.string.cancel_order_dialog_title), getString(R.string.cancel_order_confirm), data);
            }

            if (opType.equalsIgnoreCase(Constants.DELETE_CONFIRMATION)) {
                cancelOrder = (MyOrdersRespModel.MyOrders) data;
                showLoader(null);
                myOrderPresenter.callCancelOrderApi(cancelOrder.getOrderData().getOrderNumber());
            }
        }
    }

    void startOrderStatusScreen(String orderNumber) {
        Intent intent = new Intent(mActivity, MyOrderStatusActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_RESET_PASS);
        intent.putExtra(Constants.ORDER_NUMBER, orderNumber);
        startActivity(intent);
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
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
    public void getMyOrdersList() {
        if (Validation.isValidString(orderType)) {
            if (NetworkStatus.getInstance().isOnline2(context)) {
                showViewState(MultiStateView.VIEW_STATE_LOADING);
                myOrderPresenter.callMyOrdersListApi(orderType, CommonUtils.getAuthorizationKey(mActivity));
            } else
                showMsg(getString(R.string.error_no_internet));
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void setMyOrderListRes(MyOrdersRespModel data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidList(data.getOrderDataArrayList())) {
                myOrdersRespModel = data;
                setMyOrdersList();
            } else
                showViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void setCancelOrderRes(CancelOrder responce) {
        if (Validation.isValidStatus(responce)) {
            showMsg(getString(R.string.success_cancel_order));
            /*if (Validation.isValidList(orderDataArrayList) && Validation.isValidObject(cancelOrder)) {
                int notifyPos = orderDataArrayList.indexOf(cancelOrder);
                orderDataArrayList.remove(notifyPos);
                adapter.notifyItemRemoved(notifyPos);
            }*/
            getMyOrdersList();
        } else
            showMsg(getString(R.string.err_cancel_order));
    }

    private void setMyOrdersList() {
        orderDataArrayList = myOrdersRespModel.getOrderDataArrayList();
        adapter.addList(orderDataArrayList);
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        //getActivity().finish();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        getMyOrdersList();
    }


    private void setupOrdersRecyclerView() {
        layoutManager = new LinearLayoutManager(context);
        dividerItemDecoration = new DividerItemDecoration(rvMyOrder.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_4dp, null));
        adapter = new OrderListAdapter(context, orderType, this);
        rvMyOrder.setLayoutManager(layoutManager);
        rvMyOrder.addItemDecoration(dividerItemDecoration);
        rvMyOrder.setAdapter(adapter);
        rvMyOrder.setNestedScrollingEnabled(false);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mActivity = getActivity();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

}
