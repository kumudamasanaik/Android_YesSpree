package com.yesspree.app.screens.filters.refined_fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Refine;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

public class RefineTypeViewHolder extends MyViewHolder {

    @BindView(R.id.txt_item_list_title)
    TextView tvRefineType;
    @BindView(R.id.view_select)
    View viewSel;
    Context context;


    IAdapterClickListener clickListener;

    public RefineTypeViewHolder(Context context, View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.context = context;
        this.clickListener = clickListener;

        viewSel.setVisibility(View.GONE);
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Refine && Validation.isValidObject(clickListener)) {
            Refine refine = (Refine) item;
            if (Validation.isValidString(refine.getName())) {
                tvRefineType.setText(refine.getName());
                itemView.setOnClickListener(v -> clickListener.onAdapterClick(refine, getAdapterPosition(), itemView, Constants.REFINE));
            }
            if (refine.isSelected()) {
                tvRefineType.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                viewSel.setVisibility(View.VISIBLE);
            } else {
                viewSel.setVisibility(View.GONE);
                tvRefineType.setTextColor(context.getResources().getColor(R.color.person_prefix));
            }
        }
    }
}
