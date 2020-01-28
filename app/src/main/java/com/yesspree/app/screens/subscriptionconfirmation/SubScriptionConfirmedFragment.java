package com.yesspree.app.screens.subscriptionconfirmation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IPaymentSelection;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.AddSubcrInput;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.SubscribeProdRes;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.addsubscription.AddSubscriptionActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SubScriptionConfirmedFragment extends Fragment implements ISubScriptionConfirmationView, IPaymentSelection {


    @BindView(R.id.tv_subscription_confirmed)
    TextView tvSubscriptionConfirmed;
    @BindView(R.id.tv_subscription_content)
    TextView tvSubscriptionContent;
    @BindView(R.id.rdbt_delivery_closest)
    AppCompatRadioButton rdbtDeliveryClosest;
    @BindView(R.id.rdbt_not_delivery)
    AppCompatRadioButton rdbtNotDelivery;
    @BindView(R.id.rdbg_subscription)
    RadioGroup rdbgSubscription;
    @BindView(R.id.tv_ring)
    TextView tvRing;
    @BindView(R.id.tv_no_ring)
    TextView tvNoRing;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.multistateview)
    MultiStateView multistateview;

    @BindView(R.id.iv_doorbell)
    AppCompatImageView iv_doorbell;
    @BindView(R.id.tv_subscription_slot)
    TextView tvSubscrSlot;
    Unbinder unbinder;

    /*variables*/
    private OnFragmentInteractionListener mListener;
    String isDoorbellring;
    private Context context;
    private AddSubscriptionActivity activity;
    public AddSubcrInput subScrinput;
    SubScriptionConfirmationPresenter presenter;
    ProductData productData;

    public SubScriptionConfirmedFragment() {
        // Required empty public constructor
    }


    public static SubScriptionConfirmedFragment newInstance() {
        SubScriptionConfirmedFragment fragment = new SubScriptionConfirmedFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_scription_confirmed, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (getActivity() instanceof AddSubscriptionActivity) {
            this.activity = (AddSubscriptionActivity) getActivity();
            subScrinput = activity.subScrinput;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void init() {
        productData = activity.productData;
        presenter = new SubScriptionConfirmationPresenter(this);
        if (Validation.isValidString(productData.getSubscription_slot()))
            tvSubscrSlot.setText(productData.getSubscription_slot());
    }

    @OnClick({R.id.tv_ring, R.id.tv_no_ring, R.id.tv_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ring:
                subScrinput.setIs_doorbellring("1");
                CommonUtils.playDoorBellMuzic(context);
                CommonUtils.leftRotateDoorBellIcon(iv_doorbell);
                break;
            case R.id.tv_no_ring:
                subScrinput.setIs_doorbellring("0");
                break;
            case R.id.tv_done:
                if (NetworkStatus.getInstance().isOnline2(context))
                    CommonUtils.showPaymentDialog(context, this);
                else
                    showMsg(getString(R.string.error_no_internet));
                break;
        }
    }

    @Override
    public void setPaymentType(String paymentType) {
        if (Validation.isValidString(paymentType)) {
            if (paymentType.equals(getString(R.string.cod))) {
                subScrinput.setPay_type(paymentType);
                callSubScriptionConfirmApi();
            } else
                showMsg(getString(R.string.coming_soon));
        }
    }

    @OnClick({R.id.rdbt_delivery_closest, R.id.rdbt_not_delivery})
    public void onClickRadioButton(View view) {
        switch (view.getId()) {
            case R.id.rdbt_delivery_closest:
                subScrinput.setIs_alternate("1");
                break;
            case R.id.rdbt_not_delivery:
                subScrinput.setIs_alternate("0");
                break;

        }
    }

    @Override
    public void callSubScriptionConfirmApi() {
        if (Validation.isValidObject(subScrinput)) {
            showLoader(null);
            presenter.addSubscribe(CommonUtils.getAuthorizationKey(context), subScrinput);
        }
    }

    @Override
    public void setSubScriptionConfirmAPiResp(SubscribeProdRes res) {
        if (Validation.isValidStatus(res)) {
            if (Validation.isValidStrMsg(res))
                showMsg(res.getMessage());
            else
                showMsg(getString(R.string.success_subscribe));
            activity.finish();
        } else if (Validation.isValidStrMsg(res))
            showMsg(res.getMessage());
        else
            showMsg(getString(R.string.error_something_wrong));
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
    public void playMuzic() {

    }

    @Override
    public void showViewState(int state) {

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


}
