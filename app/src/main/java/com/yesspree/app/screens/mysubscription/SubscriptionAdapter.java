package com.yesspree.app.screens.mysubscription;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.modelapi.OrderData;
import com.yesspree.app.modelapi.OrderedProduct;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewholder> {

    ArrayList<MyOrdersRespModel.MyOrders> subscriptionsArrayList;
    Context context;
    IAdapterClickListener clickListener;
    String type;


    public SubscriptionAdapter(Context context, IAdapterClickListener clickListener, String type) {
        this.subscriptionsArrayList = new ArrayList<>();
        this.context = context;
        this.clickListener = clickListener;
        this.type = type;
    }

    public void addList(ArrayList<MyOrdersRespModel.MyOrders> subscriptionsArrayList) {
        this.subscriptionsArrayList.clear();
        this.subscriptionsArrayList = subscriptionsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubscriptionViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscrib, parent, false);
        return new SubscriptionViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewholder holder, int position) {
        holder.bind(subscriptionsArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return (Validation.isValidList(subscriptionsArrayList) ? subscriptionsArrayList.size() : 0);
    }

    public class SubscriptionViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_prodcut)
        AppCompatImageView ivProdcut;
        @BindView(R.id.tv_prod_name)
        TextView tvProdName;
        @BindView(R.id.tv_subscription_txtview)
        TextView tvSubscriptionTxtview;
        @BindView(R.id.tv_subscription_amt)
        TextView tvSubscriptionAmt;
        @BindView(R.id.tv_saved_price)
        TextView tvSavedPrice;
        @BindView(R.id.tv_cancel_plan)
        TextView tvCancelPlan;
        @BindView(R.id.product_detail_control)
        LinearLayout productDetailControl;

        public SubscriptionViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvCancelPlan.setVisibility(type.equals(Constants.ACTIVE) ? View.VISIBLE : View.GONE);
        }

        public void bind(MyOrdersRespModel.MyOrders myOrders) {
            if (Validation.isValidObject(myOrders) && Validation.isValidObject(myOrders.getOrderData())) {
                OrderData orderdata = myOrders.getOrderData();
                if (Validation.isValidString(orderdata.getProduct_name()))
                    tvProdName.setText(orderdata.getProduct_name());
                if (Validation.isValidString(orderdata.getProduct_pic()))
                    ImageUtils.setImage(ivProdcut, orderdata.getProduct_pic());
                if (Validation.isValidObject(myOrders.getOrderData())) {
                    if (Validation.isStrIsInt(myOrders.getOrderData().getTotalSellingDiscount()))
                        tvSavedPrice.setText(context.getString(R.string.saved_amt, myOrders.getOrderData().getTotalSellingDiscount()));
                    if (Validation.isValidString(myOrders.getOrderData().getGrand_total()))
                        tvSubscriptionAmt.setText(context.getString(R.string.rupees_amt, myOrders.getOrderData().getGrand_total()));
                }
                itemView.setOnClickListener(v -> clickListener.onAdapterClick(myOrders, getAdapterPosition(), itemView, type));
            }
        }
    }
}
