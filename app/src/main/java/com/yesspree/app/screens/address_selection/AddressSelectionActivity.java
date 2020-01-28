package com.yesspree.app.screens.address_selection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.FetchAddressResponse;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.addaddress.AddAddressActivity;
import com.yesspree.app.screens.addaddress.AdressModelToValidation;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressSelectionActivity extends SubBaseActivity implements IAddressSelectionView, IAdapterClickListener {

    @BindView(R.id.address_recyclerview)
    RecyclerView addressRecyclerview;
    @BindView(R.id.view_header_delivery_address)
    RelativeLayout viewHeaderDeliveryAddress;


    @BindView(R.id.tv_add_new_address)
    TextView tvAddNewAddress;

    @BindView(R.id.multistateview)
    MultiStateView multiStateView;


    @BindView(R.id.empty_textView)
    TextView tvEpmtyCart;
    @BindView(R.id.empty_button)
    TextView btnEmptyCart;

    private String TAG = "AddressSelectionActivity";
    private Context mContext;
    private HomeRecyclerAdapter mAddressListAdapter;
    private String source;

    IAddressSelectionPresenter addressSelectionPresenter;
    FetchAddressResponse fetchAddressResponse;
    AddressData addressData;
    AdressModelToValidation adressModelToValidation;

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.add_new_address));
        tvEpmtyCart.setText(getString(R.string.err_empty_my_addres_list));
        btnEmptyCart.setText(getString(R.string.add_some_address_));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_address_selection, fragmentLayout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        init();
    }

    @Override
    public void init() {
        mContext = AddressSelectionActivity.this;
        addressSelectionPresenter = new AddressSelectionPresenter(this);
        setSpecificScreenData();
        setRecyclerHeader();
        callApiForAddressList();
    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();

    }

    @Override
    public void callApiForAddressList() {
        if (CommonUtils.checkLoginStatus(this, getString(R.string.error_user_not_login))) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            if (NetworkStatus.getInstance().isOnline2(this)) {
                addressSelectionPresenter.getAddressListData(CommonUtils.getAuthorizationKey(this), Constants.GET);
            } else
                CommonUtils.showToastMsg(this, getString(R.string.error_no_internet), 1);
        }

    }

    @Override
    public void callApiForDeleteAddress() {
        if (CommonUtils.checkLoginStatus(this, getString(R.string.error_user_not_login))) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            if (NetworkStatus.getInstance().isOnline2(this)) {
                if (Validation.isValidObject(addressData))

                    addressSelectionPresenter.deleteAddress(CommonUtils.getAuthorizationKey(this), addressData.getId(), Constants.DELETE);
            } else
                CommonUtils.showToastMsg(this, getString(R.string.error_no_internet), 1);
        }

    }

    @Override
    public void callApiForSelectingAddress() {
        if (CommonUtils.checkLoginStatus(this, getString(R.string.error_user_not_login))) {
            showViewState(MultiStateView.VIEW_STATE_LOADING);
            if (NetworkStatus.getInstance().isOnline2(this)) {
                if (Validation.isValidObject(addressData))
                    addressSelectionPresenter.selectAddressApi(CommonUtils.getAuthorizationKey(this), addressData.getId(), Constants.SELECT_ADDRESS);
            } else
                CommonUtils.showToastMsg(this, getString(R.string.error_no_internet), 1);
        }

    }

    @Override
    public void setAddAddressListApiResp(FetchAddressResponse data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                fetchAddressResponse = data;
                setAddressData(Constants.ADDRESS_LOADED);
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void deleteAddressResponce(FetchAddressResponse data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                fetchAddressResponse = data;
                setAddressData(Constants.ADDRESS_DELETED);

            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void selectAddressResponce(FetchAddressResponse data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                fetchAddressResponse = data;
                setAddressData(Constants.ADDRESS_SELECTED);
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    private void setAddressData(String loadedType) {
        if (Validation.isValidList(fetchAddressResponse.getAddressList())) {
            mAddressListAdapter.addList(fetchAddressResponse.getAddressList());
            if (loadedType.equals(Constants.ADDRESS_SELECTED)) {
                setResult(Constants.CONFIRM_ORDER_CODE, new Intent().putExtra(Constants.SUCCESS, Constants.SUCCESS));
                finish();
            }
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        if (Validation.isValidObject(data) && data instanceof AddressData) {
            addressData = (AddressData) data;
            if (Validation.isValidString(opType)) {
                setupAdapterListner(opType);
            }

        }

        if (Validation.isValidString(opType) && opType.equalsIgnoreCase(Constants.DELETE_CONFIRMATION)) {
            setupAdapterListner(opType);
        }


    }

    private void setupAdapterListner(String opType) {
        Intent move;
        switch (opType) {
            case Constants.EDIT:
                move = new Intent(this, AddAddressActivity.class);
                move.putExtra(Constants.SOURCE, Constants.SOURCE_ADDRESS_SELECTION);
                move.putExtra(Constants.EXTRAS_ADDRESS_DATA, addressData);
                startActivity(move);
                break;
            case Constants.DELETE:
                CommonUtils.showConfirmationDialog(this, this, getString(R.string.dialog_header), getString(R.string.dialog_body), null);
                break;


            case Constants.LIST_ITEM:
                if (Validation.isValidObject(addressData)) {
                    callApiForSelectingAddress();
                }
                break;

            case Constants.DELETE_CONFIRMATION:
                callApiForDeleteAddress();
                break;


        }

    }


    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        addAddressViewClicked();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callApiForAddressList();
    }

    @OnClick(R.id.tv_checkout)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.tv_add_new_address)
    public void addAddressViewClicked() {
        Intent move = new Intent(this, AddAddressActivity.class);
        move.putExtra(Constants.SOURCE, Constants.SOURCE_ADDRESS_SELECTION);
        startActivity(move);
    }


    private void setRecyclerHeader() {


        mAddressListAdapter = new HomeRecyclerAdapter(mContext, R.layout.choose_delivery_address_list_item, this, Constants.TYPE_ADDRESS_LIST_ADAPTER);
        addressRecyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        addressRecyclerview.addItemDecoration(new InsetDecoration(mContext));
        addressRecyclerview.setAdapter(mAddressListAdapter);
        addressRecyclerview.setNestedScrollingEnabled(false);

    }


}
