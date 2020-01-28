package com.yesspree.app.screens.viewall.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 11-07-2018.
 */

public class BrandsViewAllViewHolder extends MyViewHolder {

    @BindView(R.id.iv_brand_image)
    AppCompatImageView iv_brand_image;

    @BindView(R.id.tv_brand_name)
    TextView tv_brand_name;

    Brands brands;
    IAdapterClickListener clickListener;
    String adapterType = "";

    public BrandsViewAllViewHolder(View itemView, IAdapterClickListener clickListener, String adapterType) {
        super(itemView);
        this.clickListener = clickListener;
        this.adapterType = adapterType;

    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Brands) {
            brands = (Brands) item;
            if (Validation.isValidString(brands.getPic()))
                ImageUtils.setImage(iv_brand_image, brands.getPic());
            if (Validation.isValidString(brands.getBrand_en()))
                tv_brand_name.setText(brands.getBrand_en());

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(brands, getAdapterPosition(), null, adapterType));
            }
        }

    }

}
