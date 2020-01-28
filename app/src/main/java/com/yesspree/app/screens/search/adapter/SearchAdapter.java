package com.yesspree.app.screens.search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.SearchProdRes;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder> {

    public Context context;
    IAdapterClickListener adapterClickListener;
    ArrayList<SearchProdRes.Products> arrayList;
    String searchText;


    public SearchAdapter(Context context, IAdapterClickListener adapterClickListener) {
        this.context = context;
        this.adapterClickListener= adapterClickListener;
        this.arrayList = new ArrayList<>();
    }

    /*public void addList(ArrayList<SearchProdRes.Products> arrayList) {
        this.arrayList.clone();
        this.arrayList.addAll(arrayList);
    }*/

    public void setFilter(List<SearchProdRes.Products> countryModels) {
        this.arrayList.clear();
        this.arrayList.addAll(countryModels);
        notifyDataSetChanged();
    }

    public void setFilter(List<SearchProdRes.Products> countryModels, String searchText) {
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(countryModels);
        this.searchText = searchText.toLowerCase();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(position, holder);
    }


    Spannable getSpan(String mainString, String subString) {
        Spannable spanText = null;
        for (int i = -1; (i = mainString.indexOf(subString, i + 1)) != -1; i++) {
            spanText = new SpannableString(mainString);
            spanText.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor
                            (context.getResources(), R.color.colordc0000, null)),
                    i, (i + subString.length()),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanText;
    }


    @Override
    public int getItemCount() {
        return Validation.isValidList(arrayList) ? arrayList.size() : 0;
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.ic_search)
        AppCompatImageView icSearch;
        @BindView(R.id.textView)
        AppCompatTextView tvRelSearch;

        public Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        private void bind(int position, Viewholder holder) {
            SearchProdRes.Products products = arrayList.get(position);
            holder.tvRelSearch.setText(products.getName().toLowerCase());
            String mainString = holder.tvRelSearch.getText().toString();
            if (Validation.isValidString(searchText)) {
                holder.tvRelSearch.setText((getSpan(mainString, searchText) != null) ? getSpan(mainString, searchText) : Html.fromHtml(mainString));
            } else {
                holder.tvRelSearch.setText(Html.fromHtml(mainString));
            }
            itemView.setOnClickListener(v->adapterClickListener.onAdapterClick(products,position,null,null));
        }
    }
}
