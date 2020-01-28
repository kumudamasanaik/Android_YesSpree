package com.yesspree.app.screens.banner_offersdetail.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.modelapi.TermsCondition;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FuGenX-14 on 11-05-2018.
 */

public class BannerOfferDetailViewHolder extends MyViewHolder {

    @BindView(R.id.iv_radiobtn)
    AppCompatImageView iv_radiobtn;
    @BindView(R.id.tv_query)
    TextView tv_query;


    public BannerOfferDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item)) {
            TermsCondition query = (TermsCondition) item;
            if (Validation.isValidString(query.getValue())) {
                tv_query.setText(query.getValue());
                iv_radiobtn.setVisibility(View.VISIBLE);
            } else {
                iv_radiobtn.setVisibility(View.GONE);
                tv_query.setVisibility(View.GONE);
            }


        }
    }

}
