package com.yesspree.app.screens.addaddress;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.FetchAddressResponse;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.address_selection.AddressSelectionActivity;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.yesspree.app.constatnts.Constants.ADDRESS_TYPE_HOME;

public class AddAddressActivity extends SubBaseActivity implements IAddAddressActivityView {
    private String TAG = "AddAddressActivity";
    @BindView(R.id.btn_continue)
    View btnContinue;
    @BindView(R.id.rdbg_person_pref)
    RadioGroup radioGroup;
    @BindView(R.id.rdbt_mr)
    AppCompatRadioButton rdbtMr;
    @BindView(R.id.rdbt_mrs)
    AppCompatRadioButton rdbtMrs;
    @BindView(R.id.rdbt_miss)
    AppCompatRadioButton rdbtMiss;
    @BindView(R.id.rdbt_ms)
    AppCompatRadioButton rdbtMs;
    @BindView(R.id.layout_person_prefix)
    LinearLayout layoutPersonPrefix;
    @BindView(R.id.et_name)
    AppCompatEditText etName;
    @BindView(R.id.et_flate_house)
    AppCompatEditText etFlateHouse;
    @BindView(R.id.et_street_name)
    AppCompatEditText etStreetName;
    @BindView(R.id.et_village_city)
    AppCompatEditText etVillageCity;
    @BindView(R.id.et_taluk)
    AppCompatEditText etTaluk;
    @BindView(R.id.et_district)
    AppCompatEditText etDistrict;
    @BindView(R.id.et_state)
    AppCompatEditText etState;
    @BindView(R.id.et_pincode)
    AppCompatEditText etPincode;
    @BindView(R.id.et_locality_landmark)
    AppCompatEditText etLocalityLandmark;
    @BindView(R.id.layout_add_adrs_field)
    LinearLayout layoutAddAdrsField;
    @BindView(R.id.tv_add_type)
    AppCompatTextView tvAddType;
    @BindView(R.id.tv_add_type_home)
    AppCompatTextView tvAddTypeHome;
    @BindView(R.id.tv_add_type_office)
    AppCompatTextView tvAddTypeOffice;
    @BindView(R.id.parent_address_view)
    RelativeLayout parentAddressView;
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    private String personPrefix;
    private Context mContext;
    private String source;
    private AddressData addressData;
    private String mAdressType = ADDRESS_TYPE_HOME;
    private boolean isUpdating = false;
    private Address selectedAddress;
    private String selectedCountry, selectedLatitude, selectedLongitude;
    AddAddressActivityPresenter addAddressActivityPresenter;
    AdressModelToValidation adressModelToValidation;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.add_new_address));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_adress, fragmentLayout);
        ButterKnife.bind(this);


        if (getIntent().getParcelableExtra(Constants.EXTRAS_ADDRESS_DATA) != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            addressData = (AddressData) getIntent().getParcelableExtra(Constants.EXTRAS_ADDRESS_DATA);
            MyLogUtils.d(TAG, source);
        } else {
            source = getIntent().getStringExtra(Constants.SOURCE);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();
    }

    @Override
    public void init() {
        this.mContext = AddAddressActivity.this;
        addAddressActivityPresenter = new AddAddressActivityPresenter(this);
        setupUI();
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


    public void onRadioButtonClicked(View view) {
        boolean checked = ((AppCompatRadioButton) view).isChecked();
        personPrefix = ((AppCompatRadioButton) view).getText().toString();
        MyLogUtils.d(TAG, personPrefix);
        switch (view.getId()) {
            case R.id.rdbt_mr:
                if (checked) {
                    personPrefix = getString(R.string.pref_mr);
                }
                break;
            case R.id.rdbt_mrs:
                if (checked) {
                    personPrefix = getString(R.string.pref_mrs);
                }
                break;
            case R.id.rdbt_miss:
                if (checked) {
                    personPrefix = getString(R.string.pref_miss);
                }
                break;
            case R.id.rdbt_ms:
                if (checked) {
                    personPrefix = getString(R.string.pref_ms);
                }
                break;
            default:
                break;
        }
    }


    private void setupUI() {
        if (addressData != null) {
            setupEditAddressUI();
        } else {
            setupAddAddressUI();
        }


    }

    private void setupAddAddressUI() {
        personPrefix = getString(R.string.pref_mr);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    if (s == etName.getEditableText()) {
                        CommonUtils.validateValues(etName, getString(R.string.msg_enter_valid_name));
                    } else if (s == etFlateHouse.getEditableText()) {
                        CommonUtils.validateValues(etFlateHouse, getString(R.string.msg_enter_valid_address));
                    } else if (s == etStreetName.getEditableText()) {
                        CommonUtils.validateValues(etStreetName, getString(R.string.msg_enter_valid_address));
                    } else if (s == etVillageCity.getEditableText()) {
                        CommonUtils.validateValues(etVillageCity, getString(R.string.msg_enter_valid_city));
                    } else if (s == etTaluk.getEditableText()) {
                        CommonUtils.validateValues(etTaluk, getString(R.string.msg_enter_valid_tq));
                    } else if (s == etDistrict.getEditableText()) {
                        CommonUtils.validateValues(etDistrict, getString(R.string.msg_enter_valid_dist));
                    } else if (s == etState.getEditableText()) {
                        CommonUtils.validateValues(etState, getString(R.string.msg_enter_valid_state));
                    } else if (s == etPincode.getEditableText()) {
                        CommonUtils.validateValues(etPincode, getString(R.string.msg_enter_valid_pincode));
                    } else if (s == etLocalityLandmark.getEditableText()) {
                        CommonUtils.validateValues(etLocalityLandmark, getString(R.string.msg_enter_valid_landmark));
                    }


                }
            }
        };

        etName.addTextChangedListener(textWatcher);
        etFlateHouse.addTextChangedListener(textWatcher);
        etStreetName.addTextChangedListener(textWatcher);
        etVillageCity.addTextChangedListener(textWatcher);
        etTaluk.addTextChangedListener(textWatcher);
        etDistrict.addTextChangedListener(textWatcher);
        etState.addTextChangedListener(textWatcher);
        etPincode.addTextChangedListener(textWatcher);
        etLocalityLandmark.addTextChangedListener(textWatcher);
        etName.setText(CommonUtils.getCustomerName(this));
        openAutocompleteActivity();


    }

    private void setupEditAddressUI() {
        if (Validation.isValidString(addressData.getName()))
            etName.setText(addressData.getName());

        if (Validation.isValidString(addressData.getAddress1()))
            etFlateHouse.setText(addressData.getAddress1());
        if (Validation.isValidString(addressData.getAddress2()))
            etStreetName.setText(addressData.getAddress2());


        if (Validation.isValidString(addressData.getCity()))
            etVillageCity.setText(addressData.getCity());

        if (Validation.isValidString(addressData.getTaluk()))
            etTaluk.setText(addressData.getTaluk());

        if (Validation.isValidString(addressData.getDistrict()))
            etDistrict.setText(addressData.getDistrict());

        if (Validation.isValidString(addressData.getState()))
            etState.setText(addressData.getState());

        if (Validation.isValidString(addressData.getPincode()))
            etPincode.setText(addressData.getPincode());

        if (Validation.isValidString(addressData.getLandmark()))
            etLocalityLandmark.setText(addressData.getLandmark());

        updateAddressTypUI(addressData.getAddress_type());


        if (Validation.isValidString(addressData.getPersonPrefix())) {
            personPrefix = addressData.getPersonPrefix();
            setupPersonPrefixUI(personPrefix);

        }
        if (Validation.isValidString(addressData.getLat()) && Validation.isValidString(addressData.getLon())) {
            selectedLatitude = addressData.getLat();
            selectedLongitude = addressData.getLon();
        }


    }

    private void setupPersonPrefixUI(String personPrefix) {
        if (personPrefix.equalsIgnoreCase(getString(R.string.pref_mr))) {
            rdbtMr.setChecked(true);
        } else if (personPrefix.equalsIgnoreCase(getString(R.string.pref_mrs))) {
            rdbtMrs.setChecked(true);

        } else if (personPrefix.equalsIgnoreCase(getString(R.string.pref_miss))) {
            rdbtMiss.setChecked(true);

        } else if (personPrefix.equalsIgnoreCase(getString(R.string.pref_ms))) {
            rdbtMs.setChecked(true);

        }


    }


    private void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE).setCountry("IN").build()).build(this);
            startActivityForResult(intent, Constants.REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(), 0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "Google Play Services is not available: " + GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.e("Tag", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case Constants.REQUEST_CODE_AUTOCOMPLETE:
                    if (resultCode == RESULT_OK) {
                        CommonUtils.showLoading(this, getString(R.string.please_wait), false);
                        MyLogUtils.d(TAG, "\nComplete Place-> " + data.getExtras().toString());
                        Place place = PlaceAutocomplete.getPlace(this, data);
                        MyLogUtils.d(TAG, "\nPlace-> " + place.toString());
                        MyLogUtils.d(TAG, "\nName-> " + place.getName());
                        MyLogUtils.d(TAG, "\nAddress-> " + place.getAddress());
                        MyLogUtils.d(TAG, "\nLocal-> " + place.getLocale());
                        MyLogUtils.d(TAG, "\nLatLng-> " + place.getLatLng());
                        onLocationFound(place.getName().toString(), place.getLatLng().latitude, place.getLatLng().longitude);
                    } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                        Status status = PlaceAutocomplete.getStatus(this, data);
                        MyLogUtils.d(TAG, "Error: Status = " + status.toString());

                        onCancelled();
                    } else if (resultCode == RESULT_CANCELED) {
                        MyLogUtils.d(TAG, "Canceled: Status = ");
                        onCancelled();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtils.hideLoading();
        }
    }


    private void onLocationFound(String placeName, double lat, double lon) {
        try {
            List<Address> addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(lat, lon, 1);
            if (addresses != null && addresses.size() > 0) {
                selectedAddress = addresses.get(0);
                etFlateHouse.setText(placeName + ", " + selectedAddress.getAddressLine(0));
                etStreetName.setText(selectedAddress.getAddressLine(1));
                etPincode.setText(selectedAddress.getPostalCode());
                etVillageCity.setText(selectedAddress.getLocality());
                etState.setText(selectedAddress.getAdminArea());
                selectedCountry = selectedAddress.getCountryName();
                if (selectedAddress.hasLatitude() && selectedAddress.hasLongitude()) {
                    selectedLatitude = String.valueOf(selectedAddress.getLatitude());
                    selectedLongitude = String.valueOf(selectedAddress.getLongitude());
                }
                Log.d("ok", "Selected Address " + selectedAddress.toString());
                CommonUtils.hideLoading();
            }
        } catch (IOException e) {
            e.printStackTrace();
            onAPIError();
            CommonUtils.hideLoading();
        } finally {

        }
    }


    private void onCancelled() {
        if (addressData == null && selectedAddress == null) {
            onBackPressed();
        }
    }

    private void onAPIError() {
        Toast.makeText(this, getString(R.string.error_something_wrong), Toast.LENGTH_LONG).show();
        openAutocompleteActivity();
    }

    @Optional
    @OnClick({R.id.tv_add_type_home, R.id.tv_add_type_office})
    public void AdressTypeClicked(View v) {
        switch (v.getId()) {

            case R.id.tv_add_type_home:
                updateAddressTypUI(ADDRESS_TYPE_HOME);
                break;
            case R.id.tv_add_type_office:
                updateAddressTypUI(Constants.ADDRESS_TYPE_OFFICE);

                break;
        }

    }

    private void updateAddressTypUI(String addressTypeOffice) {
        mAdressType = addressTypeOffice;
        if (addressTypeOffice.equalsIgnoreCase(ADDRESS_TYPE_HOME)) {
            tvAddTypeHome.setTextColor(getResources().getColor(R.color.app_text_black));
            tvAddTypeHome.setBackgroundResource(R.drawable.yellow_filled_rect);
            tvAddTypeOffice.setBackgroundResource(R.drawable.btn_grey_rect_outline);
            tvAddTypeOffice.setTextColor(getResources().getColor(R.color.person_prefix));
        } else {
            tvAddTypeHome.setTextColor(getResources().getColor(R.color.person_prefix));
            tvAddTypeHome.setBackgroundResource(R.drawable.btn_grey_rect_outline);
            tvAddTypeOffice.setTextColor(getResources().getColor(R.color.app_text_black));
            tvAddTypeOffice.setBackgroundResource(R.drawable.yellow_filled_rect);
        }

    }


    @OnClick(R.id.btn_continue)
    public void submitAddress() {

        if (CommonUtils.checkLoginStatus(this, getString(R.string.error_user_not_login))) {

            if (!CommonUtils.validateValues(etName, getString(R.string.msg_enter_valid_name)))
                return;
            if (!CommonUtils.validateValues(etFlateHouse, getString(R.string.msg_enter_valid_address)))
                return;

            if (!CommonUtils.validateValues(etStreetName, getString(R.string.msg_enter_valid_address)))
                return;
            if (!CommonUtils.validateValues(etVillageCity, getString(R.string.msg_enter_valid_city)))
                return;
            if (!CommonUtils.validateValues(etTaluk, getString(R.string.msg_enter_valid_tq)))
                return;
            if (!CommonUtils.validateValues(etDistrict, getString(R.string.msg_enter_valid_dist)))
                return;
            if (!CommonUtils.validateValues(etState, getString(R.string.msg_enter_valid_state)))
                return;
            if (!CommonUtils.validateValues(etLocalityLandmark, getString(R.string.msg_enter_valid_landmark)))
                return;
            if (!CommonUtils.validateValues(etPincode, getString(R.string.msg_enter_valid_pincode)))
                return;
            if (!CommonUtils.validateValues(etLocalityLandmark, getString(R.string.msg_enter_valid_landmark)))
                return;


            adressModelToValidation = new AdressModelToValidation();
            adressModelToValidation.name = etName.getText().toString();
            adressModelToValidation.flatHouseNum = etFlateHouse.getText().toString();
            adressModelToValidation.street = etStreetName.getText().toString();
            adressModelToValidation.city = etVillageCity.getText().toString();
            adressModelToValidation.taluq = etTaluk.getText().toString();
            adressModelToValidation.dist = etDistrict.getText().toString();
            adressModelToValidation.state = etState.getText().toString();
            adressModelToValidation.pincode = etPincode.getText().toString();
            adressModelToValidation.landmark = etLocalityLandmark.getText().toString();
            adressModelToValidation.addresType = mAdressType;
            adressModelToValidation.person_prefix = personPrefix;
            adressModelToValidation.customer_id = CommonUtils.getCustomerId(this);
            adressModelToValidation.phone = CommonUtils.getCustomerMobileNmbr(this);
            adressModelToValidation.country = selectedCountry;
            adressModelToValidation.lat = selectedLatitude;
            adressModelToValidation.log = selectedLongitude;
            if (Validation.isValidString(CommonUtils.getAuthorizationKey(this)))
                adressModelToValidation.authorised_key = CommonUtils.getAuthorizationKey(this);

            if (addressData != null) {
                adressModelToValidation.selected = addressData.getSelected();
                adressModelToValidation.op = Constants.UPDATE;
                adressModelToValidation.AddressID = addressData.getId();
                callEditAdressApi();
            } else {
                adressModelToValidation.selected = 1;
                adressModelToValidation.op = Constants.CREATE;
                callAddAdressApi();
            }


        }


    }


    @Override
    public void setAddAddressResp(FetchAddressResponse data) {
        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {
                if (source.equals(Constants.SOURCE_CONFIRM_ORDER)) {
                    setResult(Constants.CONFIRM_ORDER_CODE, new Intent().putExtra(Constants.SUCCESS, Constants.SUCCESS));
                    finish();
                } else {
                    FetchAddressResponse fetchAddressResponse = data;
                    CommonUtils.showToastMsg(this, getString(R.string.address_added_successfully), 1);
                    Intent move = new Intent(this, AddressSelectionActivity.class);
                    startActivity(move);
                }
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void setEditddressResp(FetchAddressResponse data) {

        if (Validation.isValidStatus(data)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(data)) {

                if(source.equalsIgnoreCase(Constants.SOURCE_EDIT_ADDRESS_MY_ACCOUNT))
                {
                    setResult(Constants.MY_ACCOUNT_CODE, new Intent().putExtra(Constants.SUCCESS, Constants.SUCCESS));
                    finish();
                }
                else {
                    FetchAddressResponse fetchAddressResponse = data;
                    CommonUtils.showToastMsg(this, getString(R.string.address_updated_successfully), 1);
                    Intent move = new Intent(this, AddressSelectionActivity.class);
                    startActivity(move);
                }
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);

    }

    @Override
    public void callAddAdressApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        if (NetworkStatus.getInstance().isOnline2(this)) {
            addAddressActivityPresenter.addAddress(adressModelToValidation);
        } else
            CommonUtils.showToastMsg(this, getString(R.string.error_no_internet), 1);


    }

    @Override
    public void callEditAdressApi() {
        showViewState(MultiStateView.VIEW_STATE_LOADING);
        if (NetworkStatus.getInstance().isOnline2(this)) {
            addAddressActivityPresenter.editAddress(adressModelToValidation);
        } else
            CommonUtils.showToastMsg(this, getString(R.string.error_no_internet), 1);

    }

    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callAddAdressApi();
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callAddAdressApi();
    }

    @OnClick({R.id.et_pincode, R.id.et_village_city, R.id.et_state, R.id.et_taluk, R.id.et_district})
    public void onClickChangeAddress() {
        openAutocompleteActivity();
    }


}
