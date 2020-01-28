package com.yesspree.app.screens.naivigationdrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.NavigationDrawerModel;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    Context context;
    ArrayList<NavigationDrawerModel> list;
    IAdapterClickListener clickListener;

    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerModel> list, IAdapterClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NavigationDrawerModel model = list.get(position);
        if (Validation.isValidString(model.getTitle())) {
            holder.tvTitle.setText(model.getTitle());
            if (model.getTitle().equalsIgnoreCase(context.getString(R.string.language))) {
                holder.tvEdit.setVisibility(View.VISIBLE);
                holder.tvEdit.setText(context.getString(R.string.english));
            } else
                holder.tvEdit.setVisibility(View.GONE);
            if (position == 1) {
                holder.ivEdit.setVisibility(View.VISIBLE);
                holder.ivEdit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_slider_edit_address));
            } else
                holder.ivEdit.setVisibility(View.GONE);
        }
        if (model.getIcon() != null)
            holder.ivIcon.setImageDrawable(model.getIcon());

        if (model.getTitle().equalsIgnoreCase(context.getString(R.string.language)) ||
                model.getTitle().equalsIgnoreCase(context.getString(R.string.shop_by_cat)) ||
                model.getTitle().equalsIgnoreCase(context.getString(R.string.faq)) ||
                model.getTitle().equalsIgnoreCase(context.getString(R.string.about_release)))
            holder.viewDiv.setVisibility(View.VISIBLE);
        else
            holder.viewDiv.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        AppCompatImageView ivIcon;
        @BindView(R.id.tv_title)
        AppCompatTextView tvTitle;
        @BindView(R.id.tv_edit)
        AppCompatTextView tvEdit;
        @BindView(R.id.iv_edit)
        AppCompatImageView ivEdit;
        @BindView(R.id.view_div)
        View viewDiv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null)
                        clickListener.onAdapterClick(list.get(getAdapterPosition()), getAdapterPosition(), null,"");
                }
            });
        }

    }
}
