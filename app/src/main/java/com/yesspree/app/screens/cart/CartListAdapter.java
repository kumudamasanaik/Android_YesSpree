package com.yesspree.app.screens.cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.Sku;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductData> productDataArrayList;
    IProductListClickListener iProductListClickListener;
    RecyclerView recyclerView;
    private CartModifyParam inputParameters;
    public String adapterType;
    private String TAG = "CartListAdapter";


    public CartListAdapter(Context context, IProductListClickListener iProductListClickListener, RecyclerView recyclerView, String adapterType) {
        this.context = context;
        this.iProductListClickListener = iProductListClickListener;
        this.recyclerView = recyclerView;
        this.adapterType = adapterType;
        productDataArrayList = new ArrayList<>();

    }

    public void addList(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList.clear();
        this.productDataArrayList.addAll(productDataArrayList);
    }

    public void updateList(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList.addAll(productDataArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.partial_product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        @BindView(R.id.tv_quant_increase_when_0)
        TextView tv_quant_increase_when_0;
        @BindView(R.id.tv_quantitycount)
        TextView tv_quantitycount;
        @BindView(R.id.weigh_control)
        View weightControlView;

        int count = 1;
        ProductData productData;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageWishlist.setImageResource(R.drawable.ic_delete);
        }


        private void bindData(int position) {
            productData = productDataArrayList.get(position);
            if (Validation.isValidString(productData.getName()))
                productName.setText(productData.getName());
            if (Validation.isValidString(productData.getImage()))
                ImageUtils.setImage(imageProduct, productData.getImage());


            if (Validation.isValidObject(productData.getSelSku()) && Validation.isValidString(productData.getSelSku().getSize()))
                tvSku.setText(productData.getSelSku().getSize());
            else
                tvSku.setText("0");

            imageProduct.setOnClickListener(view -> {
                if (Validation.isValidObject(iProductListClickListener))
                    iProductListClickListener.onClickProduct(productData, getAdapterPosition(), Constants.PRODUCT_DETAILS, adapterType);
            });

            if (!Validation.isValidObject(productData.getSelSku()) && Validation.isValidList(productData.getSkuData())) {
                productData.setSelSku(productData.getSkuData().get(0));
                setSelectedSkuPrice(productData.getSkuData().get(0));
            } else
                setSelectedSkuPrice(productData.getSelSku());

           /* weightControlView.setOnClickListener(view -> {
                CommonUtils.showSkuDialog(context, productData, data -> setSelectedSkuPrice(productData.getSelSku()));
            });*/
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

                if (sellingPrice < mrp) {
                    productSavedPrice.setVisibility(View.VISIBLE);
                    productDiscountPrice.setVisibility(View.VISIBLE);
                    CommonUtils.strikeThrText(productOriginalPrice);
                    productOriginalPrice.setTextColor(context.getResources().getColor(R.color.colorea4f4f));

                } else {
                    productSavedPrice.setVisibility(View.INVISIBLE);
                    productDiscountPrice.setVisibility(View.INVISIBLE);
                    productOriginalPrice.setPaintFlags(0);
                    productOriginalPrice.setTextColor(context.getResources().getColor(R.color.color0d9f67));

                }

                tvSku.setText(Validation.isValidString(sku.getSize()) ? sku.getSize() : "0");
                productOriginalPrice.setText((mrp > 0) ? CommonUtils.getStrWithRsSym(context, Float.parseFloat(String.valueOf(mrp))) : CommonUtils.getStrWithRsSym(context, Float.parseFloat(String.valueOf(0))));
                // CommonUtils.strikeThrText(productOriginalPrice);
                productDiscountPrice.setText((sellingPrice > 0) ? CommonUtils.getStrWithRsSym(context, (float) sellingPrice) : CommonUtils.getStrWithRsSym(context, 0));
                productSavedPrice.setText((saved > 0) ? context.getString(R.string.you_save) + String.valueOf(" " + saved) + " %" : context.getString(R.string.you_save) + " 0 %");
                setCountView(this, productData.getSelSku());
            } else {
                productSavedPrice.setVisibility(View.INVISIBLE);
                productDiscountPrice.setVisibility(View.INVISIBLE);
            }
        }

        @OnClick(R.id.image_wishlist)
        void onClickRemove() {
            if (NetworkStatus.getInstance().isOnline2(context)) {
                if (productDataArrayList.get(getAdapterPosition()).getSkuData() != null) {
                    ProductData current = productDataArrayList.get(getAdapterPosition());
                    inputParameters = new CartModifyParam();
                    inputParameters.setId_product(current.getProductId());
                    inputParameters.setId_sku(current.getSkuData().get(0).get_id());
                    inputParameters.set_session(CommonUtils.getSession(context));
                    inputParameters.set_id(CommonUtils.getCustomerId(context));
                    CommonUtils.showConfirmationDialog(context, iProductListClickListener, context.getString(R.string.remove_prod), context.getString(R.string.remove_prod_conf), inputParameters);
                    //iProductListClickListener.onClickProduct(inputParameters, getAdapterPosition(), Constants.CART_REMOVE, adapterType);
                }
            } else {
                Toast.makeText(context, context.getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
            }
        }

        /*private void removeProductFromCart(String productId, String skuId) {
            CommonUtils.showLoading(context, context.getString(R.string.please_wait), false);
            JsonObject inputParameters = new APIRequestParams().removeProductFromCart(CommonUtils.getCustomerId(context), productId, skuId, CommonUtils.getSession(context));
            new CallAddr().executeApi(AppController.getInstance().getApiInterface().removeProductFromCart(inputParameters), inputParameters, REMOVE_PRODUCT_FROM_CART, this);
        }*/

        @OnClick({R.id.tv_quant_increase, R.id.tv_quant_increase_when_0, R.id.tv_count_decrease})
        void clickForModifyCart(View view) {
            if (!CommonUtils.checkLoginStatus(context, context.getString(R.string.login_to_add_to_cart))) {
                return;
            }
            ProductData current = productDataArrayList.get(getAdapterPosition());
            if (Validation.isValidList(current.getSkuData())) {

                if (current.getSkuData().get(current.getSelectedSkuPosition()).getTempMyCart() != -1)
                    return;
                if (NetworkStatus.getInstance().isOnline2(context)) {

                    if (view.getId() == R.id.tv_quant_increase || view.getId() == R.id.tv_quant_increase_when_0) {
                        if (Float.valueOf(current.getSkuData().get(current.getSelectedSkuPosition()).getStock()) <= current.getSkuData().get(current.getSelectedSkuPosition()).getMycart()) {
                            Toast.makeText(context, context.getString(R.string.cannot_add_items_out_of_stock), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (current.getSkuData().get(current.getSelectedSkuPosition()).getMycart() < current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity()) {
                                if (current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity() > 1)
                                    Toast.makeText(context, context.getString(R.string.should_be_minimum_quantity, current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity()), Toast.LENGTH_SHORT).show();
                                current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity());
                            } else
                                current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(current.getSkuData().get(current.getSelectedSkuPosition()).getMycart() + 1);
                        }
                    } else {
                        if (current.getSkuData().get(current.getSelectedSkuPosition()).getMycart() <= current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity()) {
                            if (current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity() > 1)
                                Toast.makeText(context, context.getString(R.string.should_be_minimum_quantity, current.getSkuData().get(current.getSelectedSkuPosition()).getMin_quantity()), Toast.LENGTH_SHORT).show();
                            current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(0);
                        } else
                            current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(current.getSkuData().get(current.getSelectedSkuPosition()).getMycart() - 1);
                    }

                    inputParameters = new CartModifyParam();
                    inputParameters.setId_product(current.getProductId());
                    inputParameters.setId_sku(current.getSelSku().get_id());
                    inputParameters.setQuantity(String.valueOf(current.getSkuData().get(current.getSelectedSkuPosition()).getTempMyCart()));
                    inputParameters.set_session(CommonUtils.getSession(context));
                    inputParameters.setOp(Constants.CART_MODIFY);
                    inputParameters.set_id(CommonUtils.getCustomerId(context));
                    inputParameters.setWh_pincode("123456");

                    setCountView(this, current.getSelSku());
                    iProductListClickListener.onClickProduct(inputParameters, getAdapterPosition(), Constants.PRODUCT_CART_MODIFY1, adapterType);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
                }

            }
        }


    }

    public void setRemoveFromCart(FetchCartResponse object) {
        if (Validation.isValidStatus(object)) {
            String productId = inputParameters.getId_product();
            String skuId = inputParameters.getId_sku();
            int position = getPositionFromProductId(productId, skuId);
            if (position == -1) {
                return;
            }
            productDataArrayList.remove(position);
            notifyItemRemoved(position);
            if (object.getCartCartSummaryDataData() != null) {
                ((CartActivity) context).setCartSummeryView(object.getCartCartSummaryDataData());
                SharedPreferenceManger.saveCartData(context, new Gson().toJson(object.getCartCartSummaryDataData()));
            }
        } else
            onRemoveCartError();
    }

    private void onRemoveCartError() {
        Toast.makeText(context, context.getString(R.string.unable_to_remove_cart), Toast.LENGTH_SHORT).show();
    }

    public void setModifyCart(FetchCartResponse object) {
        try {
            if (Validation.isValidObject(inputParameters)) {
                String productId = inputParameters.getId_product();
                String skuId = inputParameters.getId_sku();
                int position = getPositionFromProductId(productId, skuId);
                if (position == -1) {
                    return;
                }
                ProductData current = productDataArrayList.get(position);
                ViewHolder viewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                if (Validation.isValidStatus(object)) {
                    if (current.getSkuData().get(current.getSelectedSkuPosition()).getTempMyCart() == 0) {
                        productDataArrayList.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        current.getSkuData().get(current.getSelectedSkuPosition()).setMycart(String.valueOf(current.getSkuData().get(current.getSelectedSkuPosition()).getTempMyCart()));
                        current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(-1);
                    }
                    //Cart Summary
                    if (object.getCartCartSummaryDataData() != null) {
                        ((CartActivity) context).setCartSummeryView(object.getCartCartSummaryDataData());
                        SharedPreferenceManger.saveCartData(context, new Gson().toJson(object.getCartCartSummaryDataData()));
                    }
                } else
                    onModifyCartError(current, viewHolder);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private int getPositionFromProductId(String productId, String skuId) {
        for (int i = 0; i < productDataArrayList.size(); i++) {
            if (productId.equalsIgnoreCase(productDataArrayList.get(i).getProductId()) && Validation.isValidList(productDataArrayList.get(i).getSkuData()) && skuId.equalsIgnoreCase(productDataArrayList.get(i).getSkuData().get(0).get_id()))
                return i;
        }
        return -1;
    }

    private void setCountView(ViewHolder viewHolder, Sku skuData) {
        viewHolder.tv_quantitycount.setText(String.valueOf(skuData.getTempMyCart() != -1 ? skuData.getTempMyCart() : skuData.getMycart()));
        if ((skuData.getTempMyCart() != -1 ? skuData.getTempMyCart() : skuData.getMycart()) <= 0) {
            viewHolder.layout_addbtnView.setVisibility(View.VISIBLE);
            viewHolder.layout_quantityView.setVisibility(View.GONE);
        } else {
            viewHolder.layout_addbtnView.setVisibility(View.GONE);
            viewHolder.layout_quantityView.setVisibility(View.VISIBLE);
        }

    }

    private void onModifyCartError(ProductData current, ViewHolder viewHolder) {
        if (current != null) {
            current.getSkuData().get(current.getSelectedSkuPosition()).setTempMyCart(-1);
            setCountView(viewHolder, current.getSkuData().get(current.getSelectedSkuPosition()));
        }
    }
}
