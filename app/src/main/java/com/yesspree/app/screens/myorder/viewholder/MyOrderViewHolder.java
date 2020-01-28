package com.yesspree.app.screens.myorder.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.modelapi.OrderData;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderViewHolder extends RecyclerView.ViewHolder {
    IAdapterClickListener clickListener;
    @BindView(R.id.tv_placed_on_text)
    TextView tvPlacedOnText;
    @BindView(R.id.tv_placed_on_date)
    TextView tvPlacedOnDate;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_order_id_text)
    TextView tvOrderIdText;
    @BindView(R.id.tv_prod_qty)
    TextView tvProdQty;
    @BindView(R.id.iv_go)
    ImageView ivGo;
    @BindView(R.id.tv_status_text)
    TextView tvStatusText;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_est_delivery_text)
    TextView tvEstDeliveryText;
    @BindView(R.id.tv_est_delivery)
    TextView tvEstDelivery;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    OrderData orderData;

    public MyOrderViewHolder(View itemView, IAdapterClickListener clickListener, String type) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.clickListener = clickListener;
        tvCancel.setVisibility(type.equals(Constants.PRESENT) ? View.VISIBLE : View.GONE);
    }


    public void bind(Object item, Context context) {

        if (Validation.isValidObject(item) && item instanceof MyOrdersRespModel.MyOrders)
            orderData = ((MyOrdersRespModel.MyOrders) item).getOrderData();

        if (Validation.isValidString(orderData.getInvoiceDate()))
            tvPlacedOnDate.setText(orderData.getInvoiceDate());

        if (Validation.isValidString(orderData.getOrderNumber()))
            tvOrderId.setText(orderData.getOrderNumber());

        if (Validation.isValidString(orderData.getDeliverySlot())) {
            if (Validation.isValidString(orderData.getDeliverySlot().split(" ")[0]))
                tvEstDelivery.setText(orderData.getDeliverySlot().split(" ")[0]);
        }

        if (Validation.isValidString(orderData.getOrderStatus()))
            tvStatus.setText(orderData.getOrderStatus());

        if (Validation.isValidString(orderData.getTotalItems()))
            tvProdQty.setText(context.getString(R.string.qty_text).concat(orderData.getTotalItems()));
        {
            if (Validation.isValidObject(clickListener))
                itemView.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(item, getAdapterPosition(), null, Constants.ORDER_DETAILS));
            if (Validation.isValidObject(clickListener))
                tvCancel.setOnClickListener((View v) ->
                        clickListener.onAdapterClick(item, getAdapterPosition(), null, Constants.CANCELLED));

        }
    }

}
