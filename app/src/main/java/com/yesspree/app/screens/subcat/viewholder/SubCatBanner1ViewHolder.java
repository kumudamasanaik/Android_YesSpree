package com.yesspree.app.screens.subcat.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.screens.home.listner.SubCatAdapterListner;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 17-05-2018.
 */

public class SubCatBanner1ViewHolder extends MyViewHolder {
    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;
    @BindView(R.id.tv_prod_name)
    AppCompatTextView tvProdName;
    Category data;
    SubCatAdapterListner clickListener;

    public SubCatBanner1ViewHolder(View itemView, SubCatAdapterListner clickListener) {
        super(itemView);
        this.clickListener = clickListener;

    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Category) {
            data = (Category) item;
            if (Validation.isValidString(data.getPic()))
                ImageUtils.setImage(ivProd, data.getPic());

            if (Validation.isValidString(data.getName()))
                tvProdName.setText(data.getName());
        }


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Validation.isValidObject(clickListener)) {

                    clickListener.onSubCatAdaptrListner(item, getAdapterPosition(), null);
                }
            }
        });



    }
}
