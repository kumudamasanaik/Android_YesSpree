package com.yesspree.app.screens.subcat.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yesspree.app.R;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.interfaces.IAdapterClickListener;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 18-05-2018.
 */

public class SubCatExploreView extends MyViewHolder {

    @BindView(R.id.ic_backgrond)
    AppCompatImageView ic_backgrond;

    @BindView(R.id.tv_explore_query)
    AppCompatTextView tv_explore_query;


    @BindView(R.id.tv_explore)
    AppCompatTextView tv_explore;

    IAdapterClickListener clickListener;


    public SubCatExploreView(View itemView,IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;

    }

    @Override
    public void bind(Object item, Context context) {


    }

}
