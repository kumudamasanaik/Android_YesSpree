package com.yesspree.app.screens.home.vieholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.Items;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class MainCatViewHolder extends MyViewHolder {

    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;
    @BindView(R.id.tv_prod_name)
    AppCompatTextView tvProdName;
    @BindView(R.id.view_selected)
    View view_selected;
    Category data;
    IAdapterClickListener clickListener;
    Items items;
    String adapterType = "";
    public MainCatViewHolder(View itemView, IAdapterClickListener clickListener, String adapterType) {
        super(itemView);
        this.clickListener = clickListener;
        this.adapterType = adapterType;
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Category) {
            this.data = (Category) item;
            if (Validation.isValidString(data.getPic())) {
                //ivProd = new AppCompatImageView(context);
                ImageUtils.setImage(ivProd, data.getPic());
            }
            if (Validation.isValidString(data.getName()))
                tvProdName.setText(data.getName());

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(item, getAdapterPosition(), null, adapterType));

            }

            if (data.isSelectedCategory()) {
                view_selected.setVisibility(View.VISIBLE);

            } else {
                view_selected.setVisibility(View.GONE);
            }

        }
        if (Validation.isValidObject(item) && item instanceof Items) {
            this.items = (Items) item;

            if (Validation.isValidList(items.getPic()) && Validation.isValidString(items.getPic().get(0).getPic()))
                ImageUtils.setImage(ivProd, items.getPic().get(0).getPic());

            if (Validation.isValidString(items.getName()))
                tvProdName.setText(items.getName());

            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(item, getAdapterPosition(), null, adapterType));
            }
        }
    }
}
