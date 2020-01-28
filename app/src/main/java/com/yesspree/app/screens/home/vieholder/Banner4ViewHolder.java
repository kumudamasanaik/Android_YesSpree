package com.yesspree.app.screens.home.vieholder;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IDialogClickListener;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class Banner4ViewHolder extends MyViewHolder {

    @BindView(R.id.image_product)
    AppCompatImageView imageProduct;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.image_wishlist)
    AppCompatImageView imageWishlist;
    @BindView(R.id.product_discount_price)
    TextView productDiscountPrice;
    @BindView(R.id.view_seperator1)
    View viewSeperator1;
    @BindView(R.id.tv_changewiehgt)
    TextView tv_changewiehgt;
    @BindView(R.id.product_original_price)
    TextView productOriginalPrice;
    @BindView(R.id.product_saved_price)
    TextView productSavedPrice;
    @BindView(R.id.tv_sku)
    TextView tvSku;

    @BindView(R.id.layout_addbtnView)
    View layout_addbtnView;

    @BindView(R.id.layout_quantityView)
    View layout_quantityView;
    @BindView(R.id.tv_count_decrease)
    TextView tv_count_decrease;
    @BindView(R.id.tv_quant_increase)
    TextView tv_quant_increase;
    @BindView(R.id.tv_quantitycount)
    TextView tv_quantitycount;
    @BindView(R.id.weigh_control)
    View weightControlView;


    ProductData productData;
    int count = 1;
    IAdapterClickListener adapterClickListener;
    private String selectedSkuId = "";
    private String adapterType = "";
    public Banner4ViewHolder(View itemView, IAdapterClickListener adapterClickListener,String adapterType) {
        super(itemView);
        this.adapterClickListener = adapterClickListener;
        this.adapterType = adapterType;
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof ProductData) {
            float saved;

            productData = (ProductData) item;
            if (Validation.isValidString(productData.getName()))
                productName.setText(productData.getName());
            if (Validation.isValidList(productData.getPic()) && Validation.isValidString(productData.getPic().get(0).getPic()))
                ImageUtils.setImage(imageProduct, productData.getPic().get(0).getPic());
            if (Validation.isValidDigit(productData.getMrp())) {
                productOriginalPrice.setText(CommonUtils.getStrWithRsSym(context, productData.getMrp()));
                CommonUtils.strikeThrText(productOriginalPrice);
                productOriginalPrice.setPaintFlags(productDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                productOriginalPrice.setText(CommonUtils.getStrWithRsSym(context, 0));
                CommonUtils.strikeThrText(productOriginalPrice);
            }
            if (Validation.isValidDigit(productData.getSelling_price()))
                productDiscountPrice.setText(CommonUtils.getStrWithRsSym(context, productData.getSelling_price()));
            else
                productDiscountPrice.setText(CommonUtils.getStrWithRsSym(context, 0));


            if (Validation.isValidDigit(productData.getSelling_price()) && (Validation.isValidDigit(productData.getMrp()))) {
                saved = (productData.getMrp() - productData.getSelling_price()) / productData.getMrp();
                if (Validation.isValidDigit(saved)) {
                    productSavedPrice.setText(context.getString(R.string.you_save).concat(" " + String.valueOf((int) saved) + " %"));
                } else
                    productSavedPrice.setText(context.getString(R.string.you_save) + " 0 %");
            } else
                productSavedPrice.setText(context.getString(R.string.you_save) + " 0 %");

            if (Validation.isValidObject(productData.getSelSku()) && Validation.isValidString(productData.getSelSku().getSize()))
                tvSku.setText(productData.getSelSku().getSize());
            else
                tvSku.setText("0");

            layout_addbtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layout_quantityView.getVisibility() == View.VISIBLE) {
                        layout_quantityView.setVisibility(View.GONE);
                        layout_addbtnView.setVisibility(View.VISIBLE);

                    } else {
                        layout_addbtnView.setVisibility(View.GONE);
                        layout_quantityView.setVisibility(View.VISIBLE);
                        tv_quantitycount.setText(String.valueOf(count));
                    }

                }
            });


            tv_quant_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // TO DO make Api Call network
                    count++;
                    tv_quantitycount.setText(String.valueOf(count));


                }
            });


            tv_count_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // TO DO make Api Call network
                    if (count > 1) {
                        count--;
                        tv_quantitycount.setText(String.valueOf(count));

                    } else {
                        tv_quantitycount.setText(String.valueOf(count));
                        layout_addbtnView.setVisibility(View.VISIBLE);
                        layout_quantityView.setVisibility(View.GONE);
                    }


                }
            });

            imageProduct.setOnClickListener(view -> {
                if (Validation.isValidObject(adapterClickListener))
                    adapterClickListener.onAdapterClick(productData, getAdapterPosition(), itemView,"");
            });

            weightControlView.setOnClickListener(view -> {
                CommonUtils.showSkuDialog(context, productData, new IDialogClickListener() {
                    @Override
                    public void selectedItem(Object data) {
                        tvSku.setText(productData.getSelSku().getSize());
                    }
                });
            });
        }
    }

}
