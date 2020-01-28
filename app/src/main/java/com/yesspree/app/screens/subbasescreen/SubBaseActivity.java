package com.yesspree.app.screens.subbasescreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yesspree.app.AppController;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.screens.cart.CartActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.Timer;

public abstract class SubBaseActivity extends AppCompatActivity implements View.OnClickListener {


    /*//main dashboard view
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fragment_layout)
    public LinearLayout fragmentLayout;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    //toolbar
    @BindView(R.id.title)
    public AppCompatTextView title;
    @BindView(R.id.tv_city_name)
    public AppCompatTextView tvCityName;
    @BindView(R.id.ic_menu_search)
    public AppCompatImageView icMenuSearch;
    @BindView(R.id.ic_menu_notification)
    public AppCompatImageView icMenuNotification;
    @BindView(R.id.ic_cart)
    public AppCompatImageView icCart;
    @BindView(R.id.cart_count)
    public TextView cartCount;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    //bottom
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView; */

    public LinearLayout fragmentLayout;
    public Toolbar toolbar;
    public AppCompatTextView title;
    public AppCompatTextView tvCityName;
    public BottomNavigationView bottomNavigationView;
    public ImageView icMenuSearch;
    public ImageView icMenuCart;
    public View cartView;
    public TextView cartCount;
    public AppCompatImageView icCart;

    public TextView mNotificationCartCount;
    public AppCompatImageView icNotificationCart;
    public Timer timer;
    public int nCounter = 0;
    private Intent intent;
    Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    String TAG = "SubBaseActivity";
    CartSummaryData cartSummaryData;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    public abstract void setSpecificScreenData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_base);
        context = SubBaseActivity.this;
        fragmentLayout = findViewById(R.id.fragment_layout);
        title = findViewById(R.id.title);
        tvCityName = findViewById(R.id.tv_city_name);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        icMenuSearch = findViewById(R.id.ic_menu_search);
        icMenuCart = findViewById(R.id.ic_cart);
        toolbar = findViewById(R.id.toolbar);
        cartCount = findViewById(R.id.cart_count);
        icCart = findViewById(R.id.ic_cart);
        cartView = findViewById(R.id.menu_cart_layout);

        mNotificationCartCount = findViewById(R.id.tv_notification_count);
        icNotificationCart = findViewById(R.id.ic_menu_notification);

        cartView.setOnClickListener(this);
        subBaseInit();
        setToolbarHidden();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSharedPreferenceChangeListener();

        cartSummaryData = SharedPreferenceManger.getCartData(context);
        if (Validation.isValidObject(cartSummaryData) && Validation.isValidString(cartSummaryData.getCart_count()))
            updateCartCount(Integer.parseInt(cartSummaryData.getCart_count()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSharedPreferenceChangeListener();
    }

    public void upDateNotificationCartCount(int count) {
        nCounter = count;
        if (nCounter > 0) {
            icNotificationCart.setColorFilter(getResources().getColor(R.color.yellow));
            mNotificationCartCount.setVisibility(View.VISIBLE);
            mNotificationCartCount.setText(String.valueOf(nCounter++));

        } else {
            mNotificationCartCount.setVisibility(View.GONE);
            icNotificationCart.setColorFilter(getResources().getColor(R.color.app_text_white));
        }
    }


    private void setToolbarHidden() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void subBaseInit() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_header_back);
        icMenuSearch.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.GONE);
        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvCityName.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_cart_layout:
                if (Validation.isValidObject(AppController.mActivity) && !(AppController.mActivity instanceof CartActivity))
                    startCartScreen(context, AppController.mActivity.getClass().getName());
                break;
        }
    }

    public void startCartScreen(Context context, String source) {
        if (CommonUtils.isLoggedIn(context)) {
            intent = new Intent(this, CartActivity.class);
            intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
            startActivity(intent);
        } else
            CommonUtils.showToastMsg(context, getString(R.string.err_login_cart), 1);
    }

    public void updateCartCount(int count) {
        nCounter = count;
        if (nCounter > 0) {
            icCart.setColorFilter(getResources().getColor(R.color.yellow));
            cartCount.setVisibility(View.VISIBLE);
            cartCount.setText(String.valueOf(nCounter++));
        } else {
            cartCount.setVisibility(View.GONE);
            icCart.setColorFilter(getResources().getColor(R.color.app_text_white));
        }
    }

    void registerSharedPreferenceChangeListener() {
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                MyLogUtils.e(TAG, "onSharedPreferenceChanged:" + key);
                if (key.equals(Constants.CART_DATA_PREF)) {
                    cartSummaryData = SharedPreferenceManger.getCartData(context);
                    if (Validation.isValidObject(cartSummaryData) && Validation.isValidString(cartSummaryData.getCart_count()))
                        updateCartCount(Integer.parseInt(cartSummaryData.getCart_count()));
                }
            }
        };
        SharedPreferenceManger.getSharedPreferences(context).registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    void unregisterSharedPreferenceChangeListener() {
        SharedPreferenceManger.getSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}
