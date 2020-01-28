package com.yesspree.app.screens.home.vieholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class Banner5ViewHolder extends MyViewHolder {

    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;
    @BindView(R.id.tv_prod_name)
    AppCompatTextView tvTitle;

    Bannerdata data;
    Brands brands;
    IAdapterClickListener adapterClickListener;
    String adapterType = "";

    public Banner5ViewHolder(View itemView, IAdapterClickListener adapterClickListener, String adapterType) {
        super(itemView);
        this.adapterClickListener = adapterClickListener;
        this.adapterType = adapterType;

    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Bannerdata) {
            data = (Bannerdata) item;
            if (Validation.isValidString(data.getPic()))
                ImageUtils.setImage(ivProd, data.getPic());
            if (Validation.isValidString(data.getTitle()))
                tvTitle.setText(data.getTitle());

            if (Validation.isValidObject(adapterClickListener)) {
                itemView.setOnClickListener((View v) ->
                        adapterClickListener.onAdapterClick(data, getAdapterPosition(), null, adapterType));
            }
        }

    }
}
