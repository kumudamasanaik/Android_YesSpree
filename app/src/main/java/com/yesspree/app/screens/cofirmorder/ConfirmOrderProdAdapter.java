package com.yesspree.app.screens.cofirmorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.Sku;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmOrderProdAdapter extends RecyclerView.Adapter<ConfirmOrderProdAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductData> productDataArrayList;

    public ConfirmOrderProdAdapter(Context context) {
        this.context = context;
        productDataArrayList = new ArrayList<>();
    }

    public void addList(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList.clear();
        this.productDataArrayList.addAll(productDataArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConfirmOrderProdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prod_confirm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderProdAdapter.ViewHolder holder, int position) {

        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return (Validation.isValidList(productDataArrayList)) ? productDataArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_product)
        AppCompatImageView imageProduct;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.image_wishlist)
        AppCompatImageView imageWishlist;
        @BindView(R.id.product_discount_price)
        TextView productDiscountPrice;
        @BindView(R.id.product_original_price)
        TextView productOriginalPrice;
        @BindView(R.id.product_saved_price)
        TextView productSavedPrice;


        @BindView(R.id.layout_addbtnView)
        View layout_addbtnView;

        @BindView(R.id.layout_quantityView)
        View layout_quantityView;
        @BindView(R.id.tv_count_decrease)
        TextView tv_count_decrease;
        @BindView(R.id.tv_quant_increase)
        TextView tv_quant_increase;
        @BindView(R.id.tv_quant_increase_when_0)
        TextView tv_quant_increase_when_0;
        @BindView(R.id.tv_quantitycount)
        TextView tv_quantitycount;
        @BindView(R.id.weigh_control)
        TextView tvSku;
        @BindView(R.id.tv_add_qty_text)
        TextView tvNoQtyText;

        ProductData productData;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageWishlist.setVisibility(View.GONE);
            layout_quantityView.setVisibility(View.GONE);
        }

        public void bindData(int position) {
            productData = productDataArrayList.get(position);


            if (Validation.isValidString(productData.getName()))
                productName.setText(productData.getName());
            if (Validation.isValidString(productData.getImage()))
                ImageUtils.setImage(imageProduct, productData.getImage());

            if (Validation.isValidList(productData.getSkuData()))
                setSelectedSkuPrice(productData.getSkuData().get(0));
        }

        public void setSelectedSkuPrice(Sku sku) {
            if (Validation.isValidObject(sku)) {
                if (!Validation.isStrIsInt(sku.getMrp()) || sku.getSelling_price() <= 0 || !Validation.isValidString(sku.getSize()))
                    return;
                double mrp = Double.parseDouble(sku.getMrp());
                double sellingPrice = sku.getSelling_price();

                int saved = 0;
                if (mrp > 0)
                    saved = (int) (((mrp - sellingPrice) / mrp) * 100);

                tvSku.setText(Validation.isValidString(sku.getSize()) ? sku.getSize() : "0");
                productOriginalPrice.setText((mrp > 0) ? CommonUtils.getStrWithRsSym(context, Float.parseFloat(String.valueOf(mrp))) : CommonUtils.getStrWithRsSym(context, Float.parseFloat(String.valueOf(0))));
                CommonUtils.strikeThrText(productOriginalPrice);
                productDiscountPrice.setText((sellingPrice > 0) ? CommonUtils.getStrWithRsSym(context, (float) sellingPrice) : CommonUtils.getStrWithRsSym(context, 0));
                productSavedPrice.setText((saved > 0) ? context.getString(R.string.you_save) + String.valueOf(" " + saved) + " %" : context.getString(R.string.you_save) + " 0 %");
                setCountView(this, sku);
            }
        }

        private void setCountView(ViewHolder viewHolder, Sku selSku) {
            viewHolder.layout_addbtnView.setVisibility(View.VISIBLE);
            tvNoQtyText.setText(context.getString(R.string.qty));
            if (Validation.isValidString(String.valueOf(selSku.getMycart())))
                tv_quant_increase_when_0.setText(String.valueOf(selSku.getMycart()));

        }
    }
}
