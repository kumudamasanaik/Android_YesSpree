package com.yesspree.app.screens.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.screens.address_selection.viewholder.AddressSelectionViewHolder;
import com.yesspree.app.screens.banner_offersdetail.viewholder.BannerOfferDetailViewHolder;
import com.yesspree.app.screens.filters.refined_fragment.RefineTypeViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner1ViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner2ViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner3ViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner4ViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner5ViewHolder;
import com.yesspree.app.screens.home.vieholder.Banner6ViewHolder;
import com.yesspree.app.screens.home.vieholder.BigSquareBannerViewHolder;
import com.yesspree.app.screens.home.vieholder.MainCatViewHolder;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.screens.notification.viewholders.NotificationViewHolder;
import com.yesspree.app.screens.offers.offers_viewholders.OffersViewHolder;
import com.yesspree.app.screens.product_detail.subscription.SubscribeProductViewholder;
import com.yesspree.app.screens.productlist.viewholder.ProductListFooterViewHolder;
import com.yesspree.app.screens.viewall.viewholder.BrandsViewAllViewHolder;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    int type;
    ArrayList itemList;
    IAdapterClickListener clickListener;
    String adapterTpe;


    public HomeRecyclerAdapter(Context context, int type, IAdapterClickListener clickListener,String adapterType) {
        this.clickListener = clickListener;
        this.context = context;
        this.type = type;
        this.adapterTpe = adapterType;
        this.itemList = new ArrayList<>();

    }

    public HomeRecyclerAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        this.itemList = new ArrayList<>();
    }


    public void addList(ArrayList<?> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    /*@Override
    public int getItemViewType(int position) {
        switch (position) {
            case R.layout.item_main_category:
                return R.layout.item_main_category;
            default:
                return R.layout.item_main_category;
        }
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(type, parent, false);

        MyViewHolder holder = null;
        switch (type) {
            case R.layout.item_main_category:
                holder = new MainCatViewHolder(view, clickListener,adapterTpe);
                break;
            case R.layout.item_banner_image1:
                holder = new Banner1ViewHolder(view,clickListener,adapterTpe);
                break;
            case R.layout.item_banner_image2:
                holder = new Banner2ViewHolder(view,clickListener,adapterTpe);
                break;
            case R.layout.item_banner_image3:
                holder = new Banner3ViewHolder(view, clickListener,adapterTpe);
                break;
            case R.layout.partial_product_list_item:
                holder = new Banner4ViewHolder(view, clickListener,adapterTpe);
                break;
            case R.layout.item_banner_image7:
                holder = new Banner5ViewHolder(view,clickListener,adapterTpe);
                break;
            case R.layout.item_banner_image8:
                holder = new Banner6ViewHolder(view,clickListener,adapterTpe);
                break;

            case R.layout.filter_refined_adapter_headers_list_item:
                holder = new RefineTypeViewHolder(context, view, clickListener);
                break;

            case R.layout.choose_delivery_address_list_item:
                holder = new AddressSelectionViewHolder(view, clickListener);
                break;


            case R.layout.notification_recycler_list_item:
                holder = new NotificationViewHolder(view);
                break;

            case R.layout.partial_offers_adapter_list_item:
                holder = new OffersViewHolder(view, clickListener);
                break;

            case R.layout.partial_user_like_products_list_items:
                holder = new ProductListFooterViewHolder(view, clickListener);
                break;

            case R.layout.adapter_offer_detail_list_item:
                holder = new BannerOfferDetailViewHolder(view);
                break;

            case R.layout.item_banner_image9:
                holder = new Banner3ViewHolder(view, clickListener,adapterTpe);
                break;

            case R.layout.partail_popup_product_subscribe_list_item:
                holder = new SubscribeProductViewholder(view, clickListener);
                break;

            case R.layout.adapter_brands_view_all_list_item:
                holder = new BrandsViewAllViewHolder(view, clickListener,adapterTpe);
                break;
            case R.layout.item_banner_image5:
                holder = new Banner2ViewHolder(view,clickListener,adapterTpe);
                break;
            case R.layout.partial_brand_list_item:
                holder = new Banner3ViewHolder(view, clickListener,adapterTpe);
                break;

            case R.layout.big_square_sliding_banner_adapter_list_item:
                holder = new BigSquareBannerViewHolder(view,clickListener,adapterTpe);
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
        // TODO: 21-06-2018 remove static size
        return (itemList != null && itemList.size() > 0) ? itemList.size() : 6;
    }
}
