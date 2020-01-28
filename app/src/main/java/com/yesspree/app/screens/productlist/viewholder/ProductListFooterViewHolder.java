package com.yesspree.app.screens.productlist.viewholder;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 28-05-2018.
 */

public class ProductListFooterViewHolder extends MyViewHolder {



    @BindView(R.id.tv_percentage)
    TextView tv_percentage;


    @BindView(R.id.product_name)
    TextView product_name;

    @BindView(R.id.tv_discount_price)
    TextView tv_discount_price;


    @BindView(R.id.tv_original_price)
    TextView tv_original_price;

    @BindView(R.id.tv_weight)
    TextView tv_weight;


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
    int count = 1;


    ProductData data;
    IAdapterClickListener clickListener;

    public ProductListFooterViewHolder(View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof ProductData) {
            this.data = (ProductData) item;
            float saved;

            data = (ProductData) item;
            if (Validation.isValidString(data.getName()))
                product_name.setText(data.getName());

            if (Validation.isValidDigit(data.getMrp())) {
                tv_original_price.setText(CommonUtils.getStrWithRsSym(context, data.getMrp()));
                CommonUtils.strikeThrText(tv_original_price);
                tv_original_price.setPaintFlags(tv_discount_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_original_price.setText(CommonUtils.getStrWithRsSym(context, 0));
                CommonUtils.strikeThrText(tv_original_price);
            }


            if (Validation.isValidDigit(data.getSelling_price()))
                tv_discount_price.setText(CommonUtils.getStrWithRsSym(context, data.getSelling_price()));
            else
                tv_discount_price.setText(CommonUtils.getStrWithRsSym(context, 0));


            if (Validation.isValidList(data.getSkuData()) && Validation.isValidString(data.getSkuData().get(0).getSize()))
                tv_weight.setText(String.valueOf(data.getSkuData().get(0).getSize()));
            else
                tv_weight.setVisibility(View.INVISIBLE);


            if (Validation.isValidDigit(data.getSelling_price()) && (Validation.isValidDigit(data.getMrp()))) {
                saved = (data.getMrp() - data.getSelling_price()) / data.getMrp();
                if (Validation.isValidDigit(saved)) {
                    tv_percentage.setText(String.valueOf((int) saved).concat( " %"));
                } else
                    tv_percentage.setText( "0 %");
            }else
                tv_percentage.setText(( "0 %"));



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


        }

    }
}
