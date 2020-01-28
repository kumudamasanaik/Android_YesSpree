package com.yesspree.app.screens.home.vieholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.Brands;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class Banner3ViewHolder extends MyViewHolder {

    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;
    @BindView(R.id.tv_prod_name)
    AppCompatTextView tvProdName;
    Bannerdata data;
    Brands brands;
    IAdapterClickListener clickListener;
    String adapterType = "";
    public Banner3ViewHolder(View itemView, IAdapterClickListener clickListener, String adapterType) {
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
            if (Validation.isValidString(data.getTitle()))
                tvProdName.setText(data.getTitle());

            if (Validation.isValidString(data.getName()))
                tvProdName.setText(data.getName());

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(data, getAdapterPosition(), null, adapterType));
            }
        }

        if (Validation.isValidObject(item) && item instanceof Brands) {
            brands = (Brands) item;
            if (Validation.isValidString(brands.getPic()))
                ImageUtils.setImage(ivProd, brands.getPic());
            if (Validation.isValidString(brands.getBrand_en()))
                tvProdName.setText(brands.getBrand_en());

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(brands, getAdapterPosition(), null, adapterType));
            }
        }

        if (Validation.isValidObject(item) && item instanceof Category) {
            Category category = (Category) item;
            if (Validation.isValidString(category.getPic()))
                ImageUtils.setImage(ivProd, category.getPic());
            if (Validation.isValidString(category.getName()))
                tvProdName.setText(category.getName());

            itemView.setOnClickListener(v -> {

                if (Validation.isValidObject(clickListener))
                    clickListener.onAdapterClick(category, getAdapterPosition(), itemView, "");
            });
        }
    }
}
