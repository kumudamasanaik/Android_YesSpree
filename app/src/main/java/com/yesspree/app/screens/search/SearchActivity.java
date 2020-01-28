package com.yesspree.app.screens.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.EditTextWithClear;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.SearchProdRes;
import com.yesspree.app.screens.product_detail.ProductDetailActivity;
import com.yesspree.app.screens.search.adapter.SearchAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.KeyboardUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements ISearchView, IAdapterClickListener {

    @BindView(R.id.et_text)
    EditTextWithClear etText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rc_search)
    RecyclerView rvSearch;

    // variables
    ArrayList<String> list;
    SearchAdapter searchAdapter;
    LinearLayoutManager layoutManager;
    Context context;
    DividerItemDecoration dividerItemDecoration;
    SearchPresenter presenter;
    ProductListInput input;
    String searchChar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                KeyboardUtils.hideSoftInput(this);
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void init() {
        context = SearchActivity.this;
        defaultSearchParam();
        presenter = new SearchPresenter(this);
        searchAdapter = new SearchAdapter(this, this::onAdapterClick);
        layoutManager = new LinearLayoutManager(context);
        rvSearch.setLayoutManager(layoutManager);
        dividerItemDecoration = new DividerItemDecoration(rvSearch.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_2dp, null));
        rvSearch.setAdapter(searchAdapter);
        rvSearch.addItemDecoration(dividerItemDecoration);
        etText.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 750; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence searchChar, int start, int before, int count) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {


                            @Override
                            public void run() {
                                if (searchChar.toString().length() > 0) {
                                    input.setSearch(searchChar.toString());
                                    SearchActivity.this.searchChar = searchChar.toString();
                                    searchProducts();
                                }
                            }
                        },
                        DELAY
                );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void defaultSearchParam() {
        input = new ProductListInput();
        input.set_id(CommonUtils.getCustomerId(context));
        input.set_session(CommonUtils.getSession(context));
        input.setWh_pincode(CommonUtils.getPincode(context));
    }


    @Override
    public void searchProducts() {
        presenter.callSearch(input);
    }

    @Override
    public void setSearchProductRes(SearchProdRes res) {
        if (Validation.isValidStatus(res) && Validation.isValidList(res.getProductsArrayList())) {
            searchAdapter.setFilter(res.getProductsArrayList(), searchChar);
        } else if (Validation.isValidStrMsg(res))
            showMsg(res.getMessage());
        else
            showMsg(getString(R.string.error_something_wrong));
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && data instanceof SearchProdRes.Products) {
            SearchProdRes.Products product = (SearchProdRes.Products) data;
            if (Validation.isValidString(product.get_id()))
                startproductDetailsScreen(product);
        }
    }

    private void startproductDetailsScreen(SearchProdRes.Products product) {
        Intent move = new Intent(context, ProductDetailActivity.class);
        move.putExtra(Constants.EXTRA_PRODUCT_ID, product.get_id());
        startActivity(move);
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 0);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), false);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

}
