package com.yesspree.app.screens.subcat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.screens.home.listner.SubCatAdapterListner;
import com.yesspree.app.screens.home.vieholder.Banner2ViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner3ViewHolder;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.screens.subcat.viewholder.SubCatBanner1ViewHolder;
import com.yesspree.app.screens.subcat.viewholder.SubCatBanner1ViewWthHeaderTittleHolder;
import com.yesspree.app.screens.subcat.viewholder.SubCatBanner4ViewHolder;
import com.yesspree.app.screens.subcat.viewholder.SubCatMainViewHolder;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 17-05-2018.
 */

public class SubCatCommonRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    int type;
    ArrayList itemList;
    IAdapterClickListener clickListener;
    SubCatAdapterListner subCatAdapterListner;
    public SubCatCommonRecyclerAdapter(Context context, int type, IAdapterClickListener clickListener, SubCatAdapterListner subCatAdapterListner) {
        this.clickListener = clickListener;
        this.subCatAdapterListner = subCatAdapterListner;
        this.context = context;
        this.type = type;
        this.itemList = new ArrayList<>();

    }
    public SubCatCommonRecyclerAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        this.itemList = new ArrayList<>();
    }
    public void addList(ArrayList<?> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(type, parent, false);

        MyViewHolder holder = null;
        switch (type) {
            case R.layout.item_main_category:
                holder = new SubCatMainViewHolder(view, clickListener);//
                break;
            case R.layout.item_banner_image2:
                holder = new Banner2ViewHolder(view, clickListener, null);//
                break;
            case R.layout.item_banner_image3:
                holder = new Banner3ViewHolder(view, clickListener,"");//
                break;
            case R.layout.item_banner_image4:
                holder = new SubCatBanner4ViewHolder(view,clickListener);//
                break;
            case R.layout.item_banner_image9:
                holder = new SubCatBanner1ViewHolder(view, subCatAdapterListner);//
                break;
            case R.layout.item_banner_10:
                holder = new SubCatBanner1ViewWthHeaderTittleHolder(view,clickListener);//
                break;
        }
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (Validation.isValidList(itemList))
            holder.bind(itemList.get(holder.getAdapterPosition()), context);
    }
    @Override
    public int getItemCount() {
        return (itemList != null && itemList.size() > 0) ? itemList.size() : 6;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
