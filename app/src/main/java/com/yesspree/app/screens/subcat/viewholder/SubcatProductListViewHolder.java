package com.yesspree.app.screens.subcat.viewholder;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 17-05-2018.
 */

public class SubcatProductListViewHolder extends MyViewHolder {
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
    ProductData data;

    public SubcatProductListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof ProductData) {
            float saved;

            data = (ProductData) item;
            if (Validation.isValidString(data.getName()))
                productName.setText(data.getName());
            if (Validation.isValidList(data.getPic()) && Validation.isValidString(data.getPic().get(0).getPic()))
                ImageUtils.setImage(imageProduct, data.getPic().get(0).getPic());
            if (Validation.isValidDigit(data.getMrp())) {
                productOriginalPrice.setText(CommonUtils.getStrWithRsSym(context, data.getMrp()));
                CommonUtils.strikeThrText(productOriginalPrice);
                productOriginalPrice.setPaintFlags(productDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                productOriginalPrice.setText(CommonUtils.getStrWithRsSym(context, 0));
                CommonUtils.strikeThrText(productOriginalPrice);
            }
            if (Validation.isValidDigit(data.getSelling_price()))
                productDiscountPrice.setText(CommonUtils.getStrWithRsSym(context, data.getSelling_price()));
            else
                productDiscountPrice.setText(CommonUtils.getStrWithRsSym(context, 0));


            if (Validation.isValidDigit(data.getSelling_price()) && (Validation.isValidDigit(data.getMrp()))) {
                saved = (data.getMrp() - data.getSelling_price()) / data.getMrp();
                if (Validation.isValidDigit(saved)) {
                    productSavedPrice.setText(context.getString(R.string.you_save) + String.valueOf((int) saved) + " %");
                } else
                    productSavedPrice.setText(context.getString(R.string.you_save) + "0 %");
            }else
                productSavedPrice.setText(context.getString(R.string.you_save) + "0 %");

            if (Validation.isValidList(data.getSkuData()) && Validation.isValidDigit(data.getSkuData().get(0).getSelling_price()))
                tvSku.setText(String.valueOf(data.getSkuData().get(0).getSelling_price()));
            else
                tvSku.setText("0");

        }
    }

}
