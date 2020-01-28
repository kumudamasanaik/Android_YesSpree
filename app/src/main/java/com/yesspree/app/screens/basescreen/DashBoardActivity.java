package com.yesspree.app.screens.basescreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.BottomNavigationViewHelper;
import com.yesspree.app.customviews.HideShowNestedScrollListener;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.DashboardResModel;
import com.yesspree.app.modelapi.NavigationDrawerModel;
import com.yesspree.app.modelapi.RateUsInputModel;
import com.yesspree.app.modelapi.RateUsRes;
import com.yesspree.app.modelapi.Refferal;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.aboutus.AboutusActivity;
import com.yesspree.app.screens.addaddress.AddAddressActivity;
import com.yesspree.app.screens.address_selection.AddressSelectionActivity;
import com.yesspree.app.screens.cart.CartActivity;
import com.yesspree.app.screens.customercare.CustomerCaresActivity;
import com.yesspree.app.screens.faq.FAQActivity;
import com.yesspree.app.screens.home.HomeFragment;
import com.yesspree.app.screens.location.CurrentLocActivity;
import com.yesspree.app.screens.myaccount.MyAccountFragment;
import com.yesspree.app.screens.myorder.MyOrderActivity;
import com.yesspree.app.screens.mysubscription.MySubscriptionActivity;
import com.yesspree.app.screens.naivigationdrawer.NavigationDrawerFragment;
import com.yesspree.app.screens.notification.NotificationActivity;
import com.yesspree.app.screens.offers.OffersFragment;
import com.yesspree.app.screens.referandsavemore.ReferAndSaveMoreActivity;
import com.yesspree.app.screens.search.SearchActivity;
import com.yesspree.app.screens.subcat.SubCatFragment;
import com.yesspree.app.screens.wishlist.WishListFragment;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yesspree.app.constatnts.Constants.REQUEST_LOCATION_CODE;

public class DashBoardActivity extends AppCompatActivity implements IDashBoardView, IAdapterClickListener,
        OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    //main dashboard view
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fragment_layout)
    LinearLayout fragmentLayout;
    @BindView(R.id.nested_scroll_view)
    public NestedScrollView nestedScrollView;

    //toolbar
    @BindView(R.id.title)
    public AppCompatTextView title;
    @BindView(R.id.tv_city_name)
    public AppCompatTextView tvCityName;
    @BindView(R.id.ic_menu_search)
    AppCompatImageView icMenuSearch;
    @BindView(R.id.ic_menu_notification)
    AppCompatImageView icMenuNotification;
    @BindView(R.id.ic_cart)
    AppCompatImageView icCart;
    @BindView(R.id.cart_count)
    TextView cartCount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //navigation view
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.scrim_insets_frameLayout)
    ScrimInsetsFrameLayout scrimInsetsFrameLayout;

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavigationView;


    //variables
    Context context;
    ArrayList<NavigationDrawerModel> navList;
    NavigationDrawerFragment navigationDrawerFrag;
    private String TAG = "DashBoardActivity";
    private Intent intent;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    CartSummaryData cartSummaryData;
    private boolean doubleBackToExitPressedOnce;
    public DashboardResModel.DashboardData dashboardData;
    DashBoardPresenter dashBoardPresenter;
    public DashboardResModel dashboardResModel;
    private Refferal refferal;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        context = DashBoardActivity.this;
        setupLocation();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        init();
    }

    private void setupLocation() {
        if (!Validation.isValidObject(SharedPreferenceManger.getAddressData(context))) {
            Intent intent = new Intent(context, CurrentLocActivity.class);
            intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH);
            startActivityForResult(intent, REQUEST_LOCATION_CODE);
            // finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (bottomNavigationView.getSelectedItemId() == R.id.action_home)
                    drawerLayout.openDrawer(GravityCompat.START);
                else if (bottomNavigationView.getSelectedItemId() == R.id.action_category)
                    onBackPressed();
                else
                    bottomNavigationView.setSelectedItemId(R.id.action_home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void init() {

        dashBoardPresenter = new DashBoardPresenter(this);
        setupNestedScrollListener();
        setUpNavigationDrawer();
        setUpBottomNavigation();
        sendServerFirebaseToken();

        cartSummaryData = SharedPreferenceManger.getCartData(context);
        if (Validation.isValidObject(cartSummaryData) && Validation.isValidString(cartSummaryData.getCart_count()))
            updateCartCount(Integer.parseInt(cartSummaryData.getCart_count()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSharedPreferenceChangeListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupNestedScrollListener() {
        nestedScrollView.setOnScrollChangeListener(new HideShowNestedScrollListener() {
            @Override
            public void onHide() {
                if (bottomNavigationView.getVisibility() == View.VISIBLE) {
                    bottomNavigationView.animate().setDuration(800).alpha(0).translationYBy(bottomNavigationView.getHeight());
                }
            }

            @Override
            public void onShow() {
                bottomNavigationView.animate().setDuration(800).alpha(1).translationY(0);
            }

            @Override
            public void endScroll() {

                fragmentLayout.setPadding(0, 0, 0, bottomNavigationView.getHeight());
            }
        });
    }

    @Override
    public void setUpNavigationDrawer() {
        if (navigationDrawerFrag == null) {
            navigationDrawerFrag = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            navigationDrawerFrag.setUpNavDrawer(R.id.scrim_insets_frameLayout, drawerLayout, this);
        }
        navigationDrawerFrag.setHeader();
    }

    @Override
    public void setUpBottomNavigation() {

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(DashBoardActivity.this);
        //manually display home fragment at first time
        setAddress();
        addFragment(HomeFragment.newInstance());
        title.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_dropdown), null, null, null);
    }

    @Override
    public void sendServerFirebaseToken() {
        if (CommonUtils.isLoggedIn(this) && Validation.isValidString(CommonUtils.getFireBaseTokenID(this))) {
            if (NetworkStatus.getInstance().isOnline2(this)) {
                dashBoardPresenter.sendFirebaseTokenToServer(this);
            }
        }

    }

    @Override
    public void setFirebaseTokenResp(ResponseModel data) {

        if (Validation.isValidStatus(data)) {

           // CommonUtils.showToastMsg(this, data.getStatus(), 1);

        } else {
            CommonUtils.showToastMsg(this, "FireBase Token status :" + data.getStatus(), 1);
        }

    }


    private void setAddress() {
        title.setText(getString(R.string.no_address));

        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.dimens_10sp));
        tvCityName.setText(getString(R.string.no_city));
        if (Validation.isValidObject(SharedPreferenceManger.getAddressData(context))) {
            AddressData addressData = SharedPreferenceManger.getAddressData(context);
            if (Validation.isValidString(addressData.getAddress1()))
                title.setText(addressData.getAddress1());
            if (Validation.isValidString(addressData.getCity())) {
                tvCityName.setText(addressData.getCity());
                tvCityName.setVisibility(View.VISIBLE);
            }

            if (Validation.isValidString(addressData.getPincode()))
                CommonUtils.setPincode(context, addressData.getPincode());


        }
    }

    @OnClick(R.id.ic_menu_search)
    void performSearch() {
        startSearchScreen();
    }


    @OnClick(R.id.menu_cart_layout)
    void PerformCartClick() {
        startCartScreen(null);
    }

    @OnClick(R.id.toolbar_address_view)
    void navigateAddressScreen() {

        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
        startActivity(intent);

    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

        switch (item.getItemId()) {
            case R.id.action_home:
                toolbar.setNavigationIcon(R.drawable.ic_more_vert_white);
                selectedFragment = HomeFragment.newInstance();
                title.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_dropdown), null, null, null);
                icMenuSearch.setVisibility(View.VISIBLE);
                setAddress();
                addFragment(selectedFragment);
                break;
            case R.id.action_category:
                toolbar.setNavigationIcon(R.drawable.ic_header_back);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.dimens_15sp));
                title.setText(getString(R.string.category));
                tvCityName.setText("");
                tvCityName.setVisibility(View.GONE);
                title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                icMenuSearch.setVisibility(View.VISIBLE);
                if (bottomNavigationView.getTag() == null) {
                    selectedFragment = SubCatFragment.newInstance();
                    selectedFragment.setArguments(sendcatFragmentData());
                    addFragment(selectedFragment);
                }
                bottomNavigationView.setTag(null);
                break;
            case R.id.action_offers:

                selectedFragment = OffersFragment.newInstance();
                toolbar.setNavigationIcon(R.drawable.ic_header_back);
                title.setText(getString(R.string.offers));
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.dimens_15sp));
                tvCityName.setVisibility(View.GONE);
                icMenuSearch.setVisibility(View.INVISIBLE);
                title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                addFragment(selectedFragment);

                break;
            case R.id.action_wishlist:
                selectedFragment = WishListFragment.newInstance();
                toolbar.setNavigationIcon(R.drawable.ic_header_back);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.dimens_15sp));
                title.setText(getString(R.string.wishlist));
                icMenuSearch.setVisibility(View.INVISIBLE);
                tvCityName.setVisibility(View.GONE);
                title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                tvCityName.setText("");
                addFragment(selectedFragment);
                break;
            case R.id.action_account:
                toolbar.setNavigationIcon(R.drawable.ic_header_back);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.dimens_15sp));
                title.setText(getString(R.string.account));
                title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                tvCityName.setText("");
                icMenuSearch.setVisibility(View.INVISIBLE);
                tvCityName.setVisibility(View.GONE);
                selectedFragment = MyAccountFragment.newInstance();
                addFragment(selectedFragment);
                break;
        }
        return true;
    }


    private Bundle sendcatFragmentData() {
        if (Validation.isValidObject(dashboardData) && Validation.isValidList(dashboardData.getCategory())) {
            Bundle bundle = new Bundle();
            for (Category category : dashboardData.getCategory())
                if (category.isSelectedCategory()) category.setSelectedCategory(false);
            dashboardData.getCategory().get(0).setSelectedCategory(true);
            bundle.putParcelableArrayList(Constants.EXTRAS_CATEGORY_LIST, dashboardData.getCategory());
            bundle.putInt(Constants.SEL_POS, 0);
            bundle.putString(Constants.EXTRAS_CATEGORY_ID, dashboardData.getCategory().get(0).get_id());
            bundle.putString(Constants.EXTRAS_CATEGORY_NAME, dashboardData.getCategory().get(0).getName());
            return bundle;
        }
        return null;
    }


    private Refferal getReferNdSaveMoreData() {
        if (Validation.isValidObject(dashboardResModel) && Validation.isValidObject(dashboardResModel.getRefferal())) {
            return dashboardResModel.getRefferal();
        }
        return null;
    }


    public void addFragment(Fragment selectedFragment) {
        if (getCurrentFrag() != null) {
            int commit;
            if (!getCurrentFrag().getClass().getSimpleName().equals(selectedFragment.getClass().getSimpleName())) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_layout, selectedFragment, selectedFragment.getClass().getSimpleName());
                commit = transaction.commit();
                MyLogUtils.d(TAG, "addFragment:" + commit);
            }
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_layout, selectedFragment, selectedFragment.getClass().getSimpleName());
            transaction.commit();
        }
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String opType) {
        if (data != null && data instanceof NavigationDrawerModel) {
            MyLogUtils.d(TAG, data.toString());
            if (navigationDrawerFrag != null) {
                navigationDrawerFrag.closeDrawer();
                navigateTo(data);
            }
        }
    }

    public void updateCartCount(int count) {
        if (count > 0) {
            icCart.setColorFilter(getResources().getColor(R.color.yellow));
            cartCount.setVisibility(View.VISIBLE);
            cartCount.setText(String.valueOf(count));
        } else {
            cartCount.setVisibility(View.GONE);
            icCart.setColorFilter(getResources().getColor(R.color.app_text_white));
        }
    }

    private void navigateTo(Object data) {
        if (data != null && data instanceof NavigationDrawerModel) {
            String title = ((NavigationDrawerModel) data).getTitle();
            intent = null;
            if (Validation.isValidString(title)) {

                if (title.equalsIgnoreCase(getString(R.string.my_cart))) {
                    startCartScreen(title);
                }

                if (title.equalsIgnoreCase(getString(R.string.about_us))) {
                    intent = new Intent(this, AboutusActivity.class);
                    intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                }

                if (title.equalsIgnoreCase(getString(R.string.rate_us))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        showRateUsDialog();
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }

                if (title.equalsIgnoreCase(getString(R.string.offers))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        if (Validation.isValidObject(bottomNavigationView) && bottomNavigationView.getSelectedItemId() != R.id.action_offers)
                            bottomNavigationView.setSelectedItemId(R.id.action_offers);
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }

                if (title.equalsIgnoreCase(getString(R.string.my_subscription))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        intent = new Intent(this, MySubscriptionActivity.class);
                        intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }

                if (title.equalsIgnoreCase(getString(R.string.refer_and_save_more))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        refferal = getReferNdSaveMoreData();
                        if (Validation.isValidObject(refferal)) {
                            intent = new Intent(this, ReferAndSaveMoreActivity.class);
                            intent.putExtra(Constants.EXTRA_REFFERAL_DATA, refferal);
                            intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                        }

                    } else
                        showMsg(getString(R.string.err_login_cart));
                }

                if (title.equalsIgnoreCase(getString(R.string.notification))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        intent = new Intent(this, NotificationActivity.class);
                        intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }

                if (title.equalsIgnoreCase(getString(R.string.faq))) {
                    intent = new Intent(this, FAQActivity.class);
                    intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                }

                if (title.equalsIgnoreCase(getString(R.string.customer_care))) {
                    if (Validation.isValidObject(dashboardResModel.getCustomerCareData())) {
                        intent = new Intent(this, CustomerCaresActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                        bundle.putParcelable(Constants.EXTRA_CUSTOMER_CARE_DATA, (Parcelable) dashboardResModel.getCustomerCareData());
                        intent.putExtras(bundle);

                    }
                }


                if (title.equalsIgnoreCase(getString(R.string.my_orders))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        intent = new Intent(this, MyOrderActivity.class);
                        intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }


                if (title.equalsIgnoreCase(getString(R.string.my_address))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        intent = new Intent(this, AddressSelectionActivity.class);
                        intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }

                if (title.equalsIgnoreCase(getString(R.string.no_address))) {
                    intent = new Intent(this, CurrentLocActivity.class);
                    intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                }


                if (title.equalsIgnoreCase(getString(R.string.shop_by_cat))) {
                    if (CommonUtils.isLoggedIn(context)) {
                        if (Validation.isValidObject(bottomNavigationView) && bottomNavigationView.getSelectedItemId() != R.id.action_category)
                            bottomNavigationView.setSelectedItemId(R.id.action_category);
                    } else
                        showMsg(getString(R.string.err_login_cart));
                }


                if (title.equalsIgnoreCase(getString(R.string.share))) {
                    refferal = getReferNdSaveMoreData();
                    if (Validation.isValidObject(refferal))
                        CommonUtils.shareApplication(this, refferal.getShare_msg());
                }
                if (title.equalsIgnoreCase(getString(R.string.logout))) {
                    CommonUtils.setLogout(context);
                }

                if (intent != null)
                    startActivity(intent);
            }
        }

    }

    private void startCartScreen(String from) {
        if (CommonUtils.isLoggedIn(context)) {
            intent = new Intent(this, CartActivity.class);
            intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
            if (from == null)
                startActivity(intent);
        } else
            showMsg(getString(R.string.err_login_cart));
    }

    private void startSearchScreen() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
        startActivity(intent);
    }


    public Fragment getCurrentFrag() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
    }


    @Override
    public void onFragmentInteraction(Object data) {
        if (getCurrentFrag() != null) {
            MyLogUtils.d(TAG, "current frag:" + getCurrentFrag().toString());
        }
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (msg != null) ? msg : context.getString(R.string.error_something_wrong), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : context.getString(R.string.please_wait), true);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }


    @Override
    public void onBackPressed() {
        navigateBackpressed();
    }

    private void navigateBackpressed() {
        FragmentManager fragments = getSupportFragmentManager();
        if (fragments.getBackStackEntryCount() > 1) {
            fragments.popBackStackImmediate();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.action_home);
            if (isHomeFragment())
                finishThisAct();
        }
    }

    private void finishThisAct() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        CommonUtils.showToastMsg(context, getString(R.string.click_back_exit), 0);
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    boolean isHomeFragment() {
        return Validation.isValidObject(getCurrentFrag()) && getCurrentFrag() instanceof HomeFragment;
    }

    public void showRateUsDialog() {
        /*rate us dialog*/
        TextView tvSubmit;
        EditText etCommit;
        TextView tvSelected;
        ImageView ivHappy;
        ImageView ivGood;
        ImageView ivNotGood;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.partial_popup_rate_us, null);
        tvSubmit = dialogView.findViewById(R.id.tv_submit);
        tvSelected = dialogView.findViewById(R.id.tv_feedback_header);
        etCommit = dialogView.findViewById(R.id.ev_comment);
        ivGood = dialogView.findViewById(R.id.iv_good);
        ivHappy = dialogView.findViewById(R.id.iv_happy);
        ivNotGood = dialogView.findViewById(R.id.iv_not_good);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        ivNotGood.setOnClickListener(v -> tvSelected.setText(getString(R.string.not_good)));
        ivGood.setOnClickListener(v -> tvSelected.setText(getString(R.string.good)));
        ivHappy.setOnClickListener(v -> tvSelected.setText(getString(R.string.happy)));
        tvSubmit.setOnClickListener(v -> {
            dialog.dismiss();
            RateUsInputModel rateUs = new RateUsInputModel();
            rateUs.setId_customer(CommonUtils.getCustomerId(context));
            rateUs.setRating(tvSelected.getText().toString());
            rateUs.setReview(etCommit.getText().toString());
            dashBoardPresenter.submitRatings(rateUs);
            showLoader(null);
        });
        dialog.show();
    }

    @Override
    public void setRateUsRes(RateUsRes res) {
        if (Validation.isValidStatus(res)) {
            showMsg(getString(R.string.submited_successfully));
        }
    }

    void registerSharedPreferenceChangeListener() {
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                MyLogUtils.e(TAG, "onSharedPreferenceChanged:" + key);
                if (key.equals(Constants.CART_DATA_PREF)) {
                    cartSummaryData = SharedPreferenceManger.getCartData(context);
                    if (Validation.isValidObject(cartSummaryData) && Validation.isValidString(cartSummaryData.getCart_count())) {
                        updateCartCount(Integer.parseInt(cartSummaryData.getCart_count()));
                        /*if (getCurrentFrag() != null && getCurrentFrag() instanceof HomeFragment)
                            ((HomeFragment) getCurrentFrag()).callDashboardApi();*/
                    }
                }
            }
        };
        SharedPreferenceManger.getSharedPreferences(context).registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    void unregisterSharedPreferenceChangeListener() {
        SharedPreferenceManger.getSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterSharedPreferenceChangeListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOCATION_CODE) {
            if (resultCode == RESULT_OK) {
                setAddress();
            }

        }
    }
}
