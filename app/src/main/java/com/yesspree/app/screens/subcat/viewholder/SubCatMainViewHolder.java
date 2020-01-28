package com.yesspree.app.screens.subcat.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 17-05-2018.
 */

public class SubCatMainViewHolder extends MyViewHolder {
    @BindView(R.id.iv_prod)
    AppCompatImageView ivProd;
    @BindView(R.id.tv_prod_name)
    AppCompatTextView tvProdName;
    @BindView(R.id.view_selected)
    View view_selected;
    Category data;
    IAdapterClickListener clickListener;

    public SubCatMainViewHolder(View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
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

            if (data.isSelectedCategory()) {
                view_selected.setVisibility(View.VISIBLE);

            } else {
                view_selected.setVisibility(View.GONE);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Validation.isValidObject(clickListener)) {
                        clickListener.onAdapterClick(item, getAdapterPosition(), null,"");
                    }
                }
            });

        }


    }
}
