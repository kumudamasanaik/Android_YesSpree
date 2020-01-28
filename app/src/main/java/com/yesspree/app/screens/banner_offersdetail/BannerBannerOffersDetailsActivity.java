package com.yesspree.app.screens.banner_offersdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.modelapi.Offers;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerBannerOffersDetailsActivity extends SubBaseActivity implements IBannerOffersDetailView {

    @BindView(R.id.iv_offers)
    AppCompatImageView ivOffers;
    @BindView(R.id.tv_offer_header)
    TextView tvOfferHeader;
    @BindView(R.id.tv_query)
    TextView tvQuery;
    @BindView(R.id.rv_terms_conditions)
    RecyclerView rvTermsConditions;

    private String TAG = "BannerBannerOffersDetailsActivity";
    private Context mContext;
    String source;
    private HomeRecyclerAdapter mAddressListAdapter;
    Offers offers;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.offer_details));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_offers_details, fragmentLayout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            source = bundle.getString(Constants.SOURCE);
            offers = bundle.getParcelable(Constants.EXTRA_OFFERS_DATA);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();

    }

    @Override
    public void init() {
        mContext = BannerBannerOffersDetailsActivity.this;

        setupOffersRecycler();

    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {

    }

    @Override
    public void hideLoader() {

    }

    private void setupOffersRecycler() {

        if (Validation.isValidObject(offers))

        {
            if (Validation.isValidString(offers.getPic()))
                ImageUtils.setImage(ivOffers, offers.getPic());

            if (Validation.isValidString(offers.getTitle()))
                tvOfferHeader.setText(offers.getTitle());


            if (Validation.isValidString(offers.getTitle()))
                tvQuery.setText(offers.getLongDesc());

            if (Validation.isValidList(offers.getTermsCondition())) {
                mAddressListAdapter = new HomeRecyclerAdapter(mContext, R.layout.adapter_offer_detail_list_item);
                rvTermsConditions.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                rvTermsConditions.addItemDecoration(new InsetDecoration(mContext));
                rvTermsConditions.setAdapter(mAddressListAdapter);
                rvTermsConditions.setNestedScrollingEnabled(false);
                mAddressListAdapter.addList(offers.getTermsCondition());
            }


        }


    }


}
