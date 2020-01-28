package com.yesspree.app.screens.filters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.FilterValue;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterValueAdapter extends RecyclerView.Adapter<FilterValueAdapter.RefinedViewHolder> {

    Context context;
    IAdapterClickListener clickListener;
    public ArrayList<FilterValue> itemList;
    String adapterType;


    public FilterValueAdapter(Context context, String adapterType, IAdapterClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.adapterType = adapterType;
        itemList = new ArrayList<>();
    }

    public void addList(ArrayList<FilterValue> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RefinedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.filter_refined_adapter_values_list_item, parent, false);
        return new RefinedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefinedViewHolder holder, int position) {

        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return (Validation.isValidList(itemList)) ? itemList.size() : 0;
    }

    public class RefinedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_item_list_title)
        TextView txtItemListTitle;
        @BindView(R.id.cbSelected)
        AppCompatCheckBox cbSelected;


        public RefinedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cbSelected.setVisibility(View.VISIBLE);
            if (adapterType.equals(Constants.SORT))
                cbSelected.setButtonDrawable(R.drawable.radio_button);
        }

        public void bind(FilterValue data) {
            if (Validation.isValidString(data.getName())) {
                txtItemListTitle.setText(data.getName());
                cbSelected.setChecked(data.isChecked() ? true : false);
            }

            cbSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (cbSelected.isPressed()) {
                    data.setChecked(isChecked);
                    clickListener.onAdapterClick(data, getAdapterPosition(), itemView, adapterType);
                }
            });

        }
    }
}
