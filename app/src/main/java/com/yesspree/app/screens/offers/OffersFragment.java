package com.yesspree.app.screens.offers;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.Offers;
import com.yesspree.app.modelapi.OffersResModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.banner_offersdetail.BannerBannerOffersDetailsActivity;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class OffersFragment extends Fragment implements IOffersView, IAdapterClickListener {
    private OnFragmentInteractionListener mListener;
    Context mContext;
    private String TAG = "OffersFragment";
    View view;
    Unbinder unbinder;
    OffersPresenter offersPresenter;
    OffersResModel offersResModel;

    private HomeRecyclerAdapter mAddressListAdapter;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;

    @BindView(R.id.recyclerview_offers)
    RecyclerView recyclerviewOffers;

    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;
    @BindView(R.id.empty_button)
    TextView btnEmptyCart;


    public OffersFragment() {
        // Required empty public constructor
    }


    public static OffersFragment newInstance() {
        OffersFragment fragment = new OffersFragment();
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
        view = inflater.inflate(R.layout.fragment_offers, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        //  mListener = null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void init() {
        offersPresenter = new OffersPresenter(this);
        setOfferData();
        getOffersData();
    }


    private void setOfferData() {
        mAddressListAdapter = new HomeRecyclerAdapter(mContext, R.layout.partial_offers_adapter_list_item, this, "");
        recyclerviewOffers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerviewOffers.addItemDecoration(new InsetDecoration(mContext));
        recyclerviewOffers.setAdapter(mAddressListAdapter);
        recyclerviewOffers.setNestedScrollingEnabled(false);
    }


    @Override
    public void getOffersData() {
        if (NetworkStatus.getInstance().isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            offersPresenter.callOffersApi();
        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void setOffersResp(OffersResModel data) {
        if (multiStateView != null) {
            if (Validation.isValidStatus(data)) {
                showViewState(MultiStateView.VIEW_STATE_CONTENT);
                offersResModel = data;
                if (Validation.isValidList(offersResModel.getOffersArrayList())) {
                    mAddressListAdapter.addList(offersResModel.getOffersArrayList());
                } else {
                    btnEmptyCart.setVisibility(View.GONE);
                    tvEpmtyCart.setText(getString(R.string.error_empty_offerlist));
                    showViewState(MultiStateView.VIEW_STATE_EMPTY);
                }

            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), true);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(mContext, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);

    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && data instanceof Offers) {
            Offers offers = (Offers) data;

            if (offers.getTargetType().equalsIgnoreCase(Constants.TYPE_DETAIL)) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SOURCE, Constants.OFFERS_DETAIL);
                bundle.putParcelable(Constants.EXTRA_OFFERS_DATA, (Parcelable) offers);
                CommonUtils.startActivity(mContext, BannerBannerOffersDetailsActivity.class, bundle);
            } else {
                CommonUtils.navigateBannerData(mContext, offers.getTargetType(), offers.getTarget());
            }

        }

    }


}
