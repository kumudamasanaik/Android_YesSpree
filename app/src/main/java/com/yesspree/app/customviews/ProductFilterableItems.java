package com.yesspree.app.customviews;

import android.widget.Filter;

import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.screens.common.ProductListAdapter;

import java.util.ArrayList;


public class ProductFilterableItems extends Filter {
    ProductListAdapter productListAdapter;
    ArrayList<ProductData> productDataArrayList;

    public ProductFilterableItems(ProductListAdapter productListAdapter, ArrayList<ProductData> productDataArrayList) {
        this.productListAdapter = productListAdapter;
        this.productDataArrayList = productDataArrayList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        //CHECK CONSTRAINT VALIDITY
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<ProductData> filteredPlayers = new ArrayList<>();

            for (int i = 0; i < productDataArrayList.size(); i++) {
                //CHECK
                if (productDataArrayList.get(i).getName().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(productDataArrayList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = productDataArrayList.size();
            results.values = productDataArrayList;

        }

        return results;

    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        productListAdapter.addList((ArrayList<ProductData>) results.values);
        productListAdapter.notifyDataSetChanged();

    }
}
