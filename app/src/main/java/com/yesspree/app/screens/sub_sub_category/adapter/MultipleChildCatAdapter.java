package com.yesspree.app.screens.sub_sub_category.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import java.util.List;

public class MultipleChildCatAdapter extends RecyclerView.Adapter<MultipleChildCatAdapter.ViewHolder> {

    private Context context;
    private List<Category> list;//=new ArrayList<>();
    IAdapterClickListener clickListener;

    public MultipleChildCatAdapter(Context context, List<Category> list, IAdapterClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.list = list;
    }


    public void addList(List<Category> itemList) {
        this.list.clear();
        this.list.addAll(itemList);
        notifyDataSetChanged();
    }



    @Override
    public MultipleChildCatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_sub_sub_cat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MultipleChildCatAdapter.ViewHolder holder, int position) {

        if (Validation.isValidString(list.get(holder.getAdapterPosition()).getName()))
            holder.tv_product_name.setText(list.get(holder.getAdapterPosition()).getName());

        if (Validation.isValidString(list.get(holder.getAdapterPosition()).getPic()))
            ImageUtils.setImage(holder.iv_product, list.get(holder.getAdapterPosition()).getPic());


        if (list.get(holder.getAdapterPosition()).getSub() != null || list.get(holder.getAdapterPosition()).getSub().size() > 0) {
            holder.subItems.setLayoutManager(new LinearLayoutManager(context));
            //holder.subItems.setNestedScrollingEnabled(false);
            MultipleChildCatAdapter adapter = new MultipleChildCatAdapter(context, list.get(holder.getAdapterPosition()).getSub(), clickListener);
            holder.subItems.setAdapter(adapter);
            holder.subItems.hasFixedSize();
            holder.subItems.setVisibility(View.GONE);
            holder.iv_expand_more.setVisibility((holder.subItems.getAdapter().getItemCount() > 0) ? View.VISIBLE : View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.subItems.getAdapter().getItemCount() > 0) {
                    if (holder.subItems.getVisibility() == View.VISIBLE) {

                        holder.subItems.setVisibility(View.GONE);
                        int rotateDegree = -180;
                        holder.iv_expand_more.animate().rotationBy(rotateDegree).start();
                    } else {
                        holder.subItems.setVisibility(View.VISIBLE);
                        int rotateDegree = 180;
                        holder.iv_expand_more.animate().rotationBy(rotateDegree).start();
                        /*holder.subItems.smoothScrollToPosition(list.get(position).getSub().size() - 1);*/
                    }
                } else
                    if(Validation.isValidObject(clickListener))clickListener.onAdapterClick(list.get(holder.getAdapterPosition()),holder.getAdapterPosition(),holder.itemView,"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        AppCompatImageView iv_product;

        TextView tv_product_name;
        RecyclerView subItems;
        ImageView iv_expand_more;


        public ViewHolder(View itemView) {
            super(itemView);

            iv_product = itemView.findViewById(R.id.iv_product);
            iv_expand_more = itemView.findViewById(R.id.iv_expand_more);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);

            subItems = itemView.findViewById(R.id.rv_subitems);
        }
    }


}
