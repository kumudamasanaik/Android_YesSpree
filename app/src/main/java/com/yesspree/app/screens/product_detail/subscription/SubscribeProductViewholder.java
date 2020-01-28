package com.yesspree.app.screens.product_detail.subscription;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.Sku;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class SubscribeProductViewholder extends MyViewHolder {

    IAdapterClickListener clickListener;
    @BindView(R.id.view_separtor_top)
    View viewSepartorTop;
    @BindView(R.id.image_product)
    AppCompatImageView imageProduct;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_saved_price)
    TextView tvSavedPrice;
    @BindView(R.id.tv_getonce)
    TextView tvGetonce;
    @BindView(R.id.view_separtor_botom)
    View viewSepartorBotom;

    //R.layout.partail_popup_product_subscribe_list_item

    public SubscribeProductViewholder(View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;

    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof ProductData) {
            ProductData productData = (ProductData) item;
            int saved = 0;
            tvHeader.setText(context.getString(R.string.subscribe_at));
            if (Validation.isValidList(productData.getPic()) && Validation.isValidString(productData.getPic().get(0).getPic()))
                ImageUtils.setImage(imageProduct, productData.getPic().get(0).getPic());

            if (Validation.isValidObject(productData.getSelSku())) {
                Sku sku = productData.getSelSku();
                if (Validation.isValidDigit(sku.getSelling_price()))
                    tvPrice.setText(CommonUtils.getStrWithRsSym(context, sku.getSelling_price()));
                if (Integer.parseInt(sku.getMrp()) > 0)
                    saved = (Integer.parseInt(sku.getMrp()) - sku.getSelling_price()) / Integer.parseInt(sku.getMrp());
                tvSavedPrice.setText(context.getString(R.string.save_more, saved));
            }

            itemView.setOnClickListener(v -> clickListener.onAdapterClick(productData, getAdapterPosition(), itemView, Constants.EXTRAS_SUB_CATEGORY_NAME));
        }
    }
}
