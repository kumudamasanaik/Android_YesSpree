package com.yesspree.app.screens.subcat.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 21-05-2018.
 */

public class SubCatBanner1ViewWthHeaderTittleHolder extends MyViewHolder {

    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;

    @BindView(R.id.tv_prod_name)
    AppCompatTextView tv_prod_name;


    @BindView(R.id.tv_prod_title)
    AppCompatTextView tv_prod_title;
    Bannerdata data;
    Brands brands;
    IAdapterClickListener clickListener;

    public SubCatBanner1ViewWthHeaderTittleHolder(View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Bannerdata) {
            data = (Bannerdata) item;
            if (Validation.isValidString(data.getPic()))
                ImageUtils.setImage(ivProd, data.getPic());

            if (Validation.isValidString(data.getName()))
                tv_prod_name.setText(data.getName());


            if (Validation.isValidString(data.getTitle()))
                tv_prod_title.setText(data.getTitle());

            itemView.setOnClickListener(v -> {

                if (Validation.isValidObject(clickListener)) {
                    clickListener.onAdapterClick(data, getAdapterPosition(), itemView, "");
                }
            });

        }

    }
}
