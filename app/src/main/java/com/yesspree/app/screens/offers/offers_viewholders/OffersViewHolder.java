package com.yesspree.app.screens.offers.offers_viewholders;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Offers;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FuGenX-14 on 04-05-2018.
 */

public class OffersViewHolder extends MyViewHolder {

    @BindView(R.id.iv_offer_image)
    AppCompatImageView iv_offer_image;

    @BindView(R.id.tv_offer_header_name)
    TextView tv_offer_header_name;

    @BindView(R.id.tv_offer_sub_name)
    TextView tv_offer_sub_name;

    @BindView(R.id.tv_offer_promocode)
    TextView tv_offer_promocode;
    private Offers curOffer;
    IAdapterClickListener clickListener;

    public OffersViewHolder(View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Object offersItem, Context context) {
        if (Validation.isValidObject(offersItem) && offersItem instanceof Offers) {
            this.curOffer = (Offers) offersItem;

            if (Validation.isValidString(curOffer.getPic()))
                ImageUtils.setImage(iv_offer_image, curOffer.getPic());

            if (Validation.isValidString(curOffer.getTitle()))
                tv_offer_header_name.setText(curOffer.getTitle());


            if (Validation.isValidString(curOffer.getShortDesc()))
                tv_offer_sub_name.setText(curOffer.getShortDesc());


            if (Validation.isValidString(curOffer.getPromoCode()))
                tv_offer_promocode.setText(Constants.PROMO_CODE.concat(curOffer.getPromoCode()));

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(curOffer, getAdapterPosition(), null,""));

            }



        }
    }
}
