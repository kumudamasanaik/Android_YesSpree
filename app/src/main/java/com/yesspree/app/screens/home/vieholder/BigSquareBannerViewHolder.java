package com.yesspree.app.screens.home.vieholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class BigSquareBannerViewHolder extends MyViewHolder {


    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;

    Bannerdata data;
    IAdapterClickListener clickListener;
    String adapterType = "";

    public BigSquareBannerViewHolder(View itemView, IAdapterClickListener clickListener, String adapterType) {
        super(itemView);
        this.clickListener = clickListener;
        this.adapterType = adapterType;
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Bannerdata) {
            data = (Bannerdata) item;
            if (Validation.isValidString(data.getPic()))
                ImageUtils.setImage(ivProd, data.getPic());

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(data, getAdapterPosition(), null, adapterType));
            }
        }
    }


}
