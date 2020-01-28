package com.yesspree.app.screens.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.Bannerdata;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

public class BannerApter extends PagerAdapter {
    private final int type;
    Context context;
    ArrayList itemList;
    public static int LOOPS_COUNT = 1000;
    IAdapterClickListener clickListener;
    String adapterType = "";

    public BannerApter(Context context, ArrayList<Object> itemList, int type) {
        this.type = type;
        this.context = context;
        this.itemList = itemList;
    }

    public BannerApter(Context context, int type, IAdapterClickListener clickListener, String adapterType) {
        this.clickListener = clickListener;
        this.adapterType = adapterType;
        this.context = context;
        this.type = type;
        this.itemList = new ArrayList<>();

    }

    public void addArrayList(ArrayList<?> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO: 26-04-2018 remove static count
        return (itemList != null && itemList.size() > 0) ? itemList.size() : 10;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = null;
        Bannerdata bannerdata = null;
        switch (type) {
            case R.layout.item_banner:
                if (Validation.isValidList(itemList)) {
                    bannerdata = (Bannerdata) itemList.get(position);
                    itemView = LayoutInflater.from(context).inflate(type, container, false);
                    if (Validation.isValidString(bannerdata.getPic()))
                        ImageUtils.setImage(((AppCompatImageView) itemView), bannerdata.getPic());

                    if (Validation.isValidObject(clickListener)) {
                        itemView.setOnClickListener((View v) ->
                                clickListener.onAdapterClick(itemList.get(position), position,null, adapterType));
                    }
                    break;
                }
            case R.layout.item_banner_image5:
                if (Validation.isValidList(itemList)) {
                    bannerdata = (Bannerdata) itemList.get(position);
                    itemView = LayoutInflater.from(context).inflate(type, container, false);
                    AppCompatImageView imageView = itemView.findViewById(R.id.iv_prod);
                    AppCompatTextView textView = itemView.findViewById(R.id.tv_prod_name);
                    if (Validation.isValidString(bannerdata.getPic()))
                        ImageUtils.setImage(imageView, bannerdata.getPic());

                    if (Validation.isValidObject(clickListener)) {
                        itemView.setOnClickListener((View v) ->
                                clickListener.onAdapterClick(itemList.get(position), position, null, adapterType));
                    }
                    break;
                }

            case R.layout.partial_product_detail_swipe_imageview_list_item:

                if (Validation.isValidList(itemList) && itemList.get(position) instanceof Bannerdata) {
                    bannerdata = (Bannerdata) itemList.get(position);
                    itemView = LayoutInflater.from(context).inflate(type, container, false);
                    AppCompatImageView imageView = itemView.findViewById(R.id.image);
                    if (Validation.isValidString(bannerdata.getPic()))
                        ImageUtils.setImage(imageView, bannerdata.getPic());
                    if (Validation.isValidObject(clickListener)) {
                        itemView.setOnClickListener((View v) ->
                                clickListener.onAdapterClick(itemList.get(position), position, null, adapterType));
                    }
                    break;

                }

                if (Validation.isValidList(itemList) && itemList.get(position) instanceof ProductData) {
                    ProductData productData = (ProductData) itemList.get(position);
                    itemView = LayoutInflater.from(context).inflate(type, container, false);
                    AppCompatImageView imageView = itemView.findViewById(R.id.image);
                    if (Validation.isValidString(productData.getPic().get(0).getPic()))
                        ImageUtils.setImage(imageView, productData.getPic().get(0).getPic());
                    break;
                }
        }
        if (itemView != null)
            container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
