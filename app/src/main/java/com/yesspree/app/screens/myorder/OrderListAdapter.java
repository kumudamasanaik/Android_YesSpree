package com.yesspree.app.screens.myorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.screens.myorder.viewholder.MyOrderViewHolder;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<MyOrderViewHolder> {

    Context context;
    String orderType;
    IAdapterClickListener clickListener;
    ArrayList<MyOrdersRespModel.MyOrders> itemList;

    public OrderListAdapter(Context context, String orderType, IAdapterClickListener clickListener) {
        this.context = context;
        this.orderType = orderType;
        this.clickListener = clickListener;
        itemList = new ArrayList<>();
    }

    public void addList(ArrayList<MyOrdersRespModel.MyOrders> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_ordertest, parent, false);
        return new MyOrderViewHolder(view, clickListener, orderType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        holder.bind(itemList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return (Validation.isValidList(itemList)) ? itemList.size() : 0;
    }
}
